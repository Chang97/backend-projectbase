package com.base.application.code.usecase.query.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.GetCodeUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.result.CodeResult;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodeUseCaseImpl implements GetCodeUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public CodeResult handle(Long codeId) {
        return codePersistencePort.findById(codeId)
                .map(codeResultAssembler::toResult)
                .orElseThrow(() -> new NotFoundException("Code not found"));
    }
}
