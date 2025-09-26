package com.base.application.code.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.code.Code;
import com.base.domain.code.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeQueryServiceImpl implements CodeQueryService {

    private final CodeRepository codeRepository;

    @Override
    public List<Code> getCodes() {
        return codeRepository.findAll();
    }

    @Override
    public Code getCode(Long id) {
        return codeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Code not found"));
    }
}
