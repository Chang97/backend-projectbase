package com.base.application.code.usecase.query.list;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.GetCodesUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.query.condition.CodeSearchCondition;
import com.base.application.code.usecase.result.CodeResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesUseCaseImpl implements GetCodesUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public List<CodeResult> handle(CodeSearchCondition condition) {
        CodeSearchCondition criteria = condition != null ? condition : new CodeSearchCondition();
        criteria.normalize();
        return codePersistencePort.search(criteria).stream()
                .map(codeResultAssembler::toResult)
                .toList();
    }
}
