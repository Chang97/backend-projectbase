package com.base.application.code.usecase.query.list;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.GetCodesByUpperCodeUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.result.CodeResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesByUpperCodeUseCaseImpl implements GetCodesByUpperCodeUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public List<CodeResult> handle(String upperCode) {
        return codePersistencePort.findActiveChildrenByUpperCode(upperCode).stream()
                .map(codeResultAssembler::toResult)
                .toList();
    }
}
