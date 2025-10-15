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
        Sort sort = Sort.by(Sort.Order.asc("orderPath"));
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
