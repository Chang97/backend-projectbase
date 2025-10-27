package com.base.shared.code.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.shared.code.adapter.in.web.query.dto.CodeQueryResponse;
import com.base.shared.code.application.query.dto.CodeQueryResult;


@Component
public class CodeQueryResponseMapper {

    public CodeQueryResponse toResponse(CodeQueryResult result) {
        return new CodeQueryResponse(
            result.codeId(),
            result.upperCodeId(),
            result.code(),
            result.codeName(),
            result.description(),
            result.srt(),
            result.orderPath(),
            result.etc1(),
            result.etc2(),
            result.etc3(),
            result.etc4(),
            result.useYn()
        );
    }

    public List<CodeQueryResponse> toResponse(List<CodeQueryResult> results) {
        return results.stream()
            .map(this::toResponse)
            .toList();
    }
}
