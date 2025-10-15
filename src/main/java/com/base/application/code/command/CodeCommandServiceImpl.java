package com.base.application.code.command;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.api.code.mapper.CodeMapper;
import com.base.domain.code.Code;
import com.base.domain.code.CodeRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Validated
public class CodeCommandServiceImpl implements CodeCommandService {

    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public CodeResponse createCode(CodeRequest request) {
        if (codeRepository.existsByCode(request.code())) {
            throw new ConflictException("Code already exists: " + request.code());
        }
        Code code = codeMapper.toEntity(request);
        applyUpperCode(code, request.upperCodeId());
        refreshOrderPath(code);
        Code saved = codeRepository.save(code);
        return codeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CodeResponse updateCode(Long id, CodeRequest request) {
        Code existing = codeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Code not found"));

        if (!existing.getCode().equals(request.code())
                && codeRepository.existsByCode(request.code())) {
            throw new ConflictException("Code already exists: " + request.code());
        }

        codeMapper.updateFromRequest(request, existing);
        applyUpperCode(existing, request.upperCodeId());
        refreshOrderPathRecursively(existing);
        return codeMapper.toResponse(existing);
    }

    @Override
    @Transactional
    public void deleteCode(Long id) {
        Code existing = codeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.setUseYn(false); // soft delete
        codeRepository.save(existing);
    }

    private void applyUpperCode(Code code, Long upperCodeId) {
        if (upperCodeId != null) {
            code.setUpperCode(entityManager.getReference(Code.class, upperCodeId));
        } else {
            code.setUpperCode(null);
        }
    }

    private void refreshOrderPathRecursively(Code code) {
        refreshOrderPath(code);
        if (code.getCodeId() == null) {
            return;
        }
        List<Code> children = codeRepository.findByUpperCode_CodeId(code.getCodeId());
        for (Code child : children) {
            refreshOrderPathRecursively(child);
        }
    }

    private void refreshOrderPath(Code code) {
        String segment = buildOrderSegment(code.getSrt(), code.getCode());
        Code parent = code.getUpperCode();
        if (parent != null) {
            String parentPath = parent.getOrderPath();
            code.setOrderPath(parentPath != null && !parentPath.isBlank() ? parentPath + ">" + segment : segment);
        } else {
            code.setOrderPath(segment);
        }
    }

    private String buildOrderSegment(Integer srt, String codeValue) {
        int order = srt != null ? srt : 999999;
        String padded = String.format("%06d", Math.min(order, 999999));
        return padded + ":" + (codeValue == null ? "" : codeValue);
    }
}
