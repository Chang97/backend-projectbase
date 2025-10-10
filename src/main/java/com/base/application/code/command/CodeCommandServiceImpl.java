package com.base.application.code.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return codeMapper.toResponse(codeRepository.save(code));
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
        return codeMapper.toResponse(codeRepository.save(existing));
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

    
}

