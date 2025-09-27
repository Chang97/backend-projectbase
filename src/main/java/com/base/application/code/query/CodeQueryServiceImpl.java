package com.base.application.code.query;

import java.util.List;

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
    public List<CodeResponse> getCodes() {
        return codeMapper.toResponseList(codeRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeResponse> getCodesByUpperId(Long upperCodeId) {
        return codeMapper.toResponseList(
                codeRepository.findByUpperCode_CodeIdAndUseYnTrueOrderBySrtAsc(upperCodeId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeResponse> getCodesByUpperCode(String upperCode) {
        return codeMapper.toResponseList(
                codeRepository.findByUpperCode_CodeAndUseYnTrueOrderBySrtAsc(upperCode)
        );
    }
}
