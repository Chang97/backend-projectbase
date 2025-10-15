package com.base.application.code.query;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.code.dto.CodeResponse;
import com.base.api.code.mapper.CodeMapper;
import com.base.domain.code.CodeRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeQueryServiceImpl implements CodeQueryService {

    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;

    @Override
    @Transactional(readOnly = true)
    public CodeResponse getCode(Long id) {
        return codeMapper.toResponse(codeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Code not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeResponse> getCodes(CodeSearchCondition condition) {
        CodeSearchCondition criteria = condition != null ? condition : new CodeSearchCondition();
        criteria.normalize();
        // 상위 코드 → 정렬 순서 → 코드값 순으로 정렬해 계층 구조가 자연스럽게 보이도록 구성한다.
        Sort sort = Sort.by(
                Sort.Order.asc("upperCode.codeId"),
                Sort.Order.asc("srt"),
                Sort.Order.asc("code")
        );
        return codeRepository.findAll(CodeSpecifications.withCondition(criteria), sort).stream()
                .map(codeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeResponse> getCodesByUpperId(Long upperCodeId) {
        return codeRepository.findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(upperCodeId).stream()
                .map(codeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeResponse> getCodesByUpperCode(String upperCode) {
        return codeRepository.findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(upperCode).stream()
                .map(codeMapper::toResponse)
                .toList();
    }
}
