package com.base.contexts.code.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.query.dto.CodeQueryResult;
import com.base.contexts.code.application.query.mapper.CodeQueryMapper;
import com.base.contexts.code.application.query.port.in.GetCodeUseCase;
import com.base.contexts.code.domain.port.out.CodeRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodeHandler implements GetCodeUseCase {

    private final CodeRepository codeRepository;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public CodeQueryResult handle(Long codeId) {
        return codeRepository.findById(codeId)
                .map(codeQueryMapper::toResult)
                .orElseThrow(() -> new NotFoundException("Code not found"));
    }
}
