package com.base.shared.code.adapter.in.web.query.mapper;

import org.springframework.stereotype.Component;

import com.base.shared.code.adapter.in.web.query.dto.CodeQueryRequest;
import com.base.shared.code.application.query.dto.CodeQuery;


@Component
public class CodeQueryRequestMapper {

    public CodeQuery toQuery(CodeQueryRequest request) {
        return new CodeQuery(
            request.upperCodeId(),
            request.upperCode(),
            request.code(),
            request.codeName(),
            request.useYn()
        );
    }

}
