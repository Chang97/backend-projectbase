package com.base.shared.code.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.shared.code.application.query.dto.CodeQuery;
import com.base.shared.code.application.query.dto.CodeQueryResult;
import com.base.shared.code.application.query.mapper.CodeQueryMapper;
import com.base.shared.code.application.query.port.in.GetCodesUseCase;
import com.base.shared.code.domain.model.CodeFilter;
import com.base.shared.code.domain.port.out.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesHandler implements GetCodesUseCase {

    private final CodeRepository codeRepository;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public List<CodeQueryResult> handle(CodeQuery condition) {
        CodeQuery effective = condition == null
                ? new CodeQuery(null, null, null, null, null)
                : condition;
        CodeFilter filter = new CodeFilter(
                effective.upperCodeId(),
                effective.upperCode(),
                effective.code(),
                effective.codeName(),
                effective.useYn()
        );

        return codeRepository.search(filter).stream()
                .map(codeQueryMapper::toResult)
                .toList();
    }
}
