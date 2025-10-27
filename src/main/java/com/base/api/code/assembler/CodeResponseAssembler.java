package com.base.api.code.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.code.dto.CodeResponse;
import com.base.application.code.usecase.result.CodeResult;

@Component
public class CodeResponseAssembler {

    public CodeResponse toResponse(CodeResult result) {
        return new CodeResponse(
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
                result.useYn(),
                result.depth(),
                result.path()
        );
    }

    public List<CodeResponse> toResponses(List<CodeResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
