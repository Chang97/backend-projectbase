package com.base.contexts.code.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.query.dto.CodeQueryResult;
import com.base.contexts.code.application.query.mapper.CodeQueryMapper;
import com.base.contexts.code.application.query.port.in.GetCodesByUpperCodeUseCase;
import com.base.contexts.code.domain.port.out.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesByUpperCodeHandler implements GetCodesByUpperCodeUseCase {

    private final CodeRepository codeRepository;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public List<CodeQueryResult> handle(String upperCode) {
        return codeRepository.findActiveChildrenByUpperCode(upperCode)
                .stream()
                .map(codeQueryMapper::toResult)
                .toList();
    }
}
