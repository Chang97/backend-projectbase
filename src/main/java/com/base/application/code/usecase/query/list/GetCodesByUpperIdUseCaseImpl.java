package com.base.application.code.usecase.query.list;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.GetCodesByUpperIdUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.result.CodeResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesByUpperIdUseCaseImpl implements GetCodesByUpperIdUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public List<CodeResult> handle(Long upperCodeId) {
        return codePersistencePort.findActiveChildrenByUpperId(upperCodeId).stream()
                .map(codeResultAssembler::toResult)
                .toList();
    }
}
