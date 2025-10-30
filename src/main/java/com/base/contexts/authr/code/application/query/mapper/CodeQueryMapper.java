package com.base.contexts.authr.code.application.query.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.code.application.query.dto.CodeQueryResult;
import com.base.contexts.authr.code.domain.model.Code;

@Component
public class CodeQueryMapper {

    public CodeQueryResult toResult(Code domain) {
        Long codeId = domain.getCodeId() != null ? domain.getCodeId().codeId() : null;
        Long upperCodeId = domain.getUpperCodeId() != null ? domain.getUpperCodeId().codeId() : null;
        return new CodeQueryResult(
                codeId,
                upperCodeId,
                domain.getCode(),
                domain.getCodeName(),
                domain.getDescription(),
                domain.getSrt(),
                domain.getOrderPath(),
                domain.getEtc1(),
                domain.getEtc2(),
                domain.getEtc3(),
                domain.getEtc4(),
                Boolean.TRUE.equals(domain.getUseYn())
        );
    }
}
