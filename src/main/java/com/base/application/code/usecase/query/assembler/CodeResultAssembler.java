package com.base.application.code.usecase.query.assembler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.code.usecase.result.CodeResult;
import com.base.domain.code.Code;

@Component
public class CodeResultAssembler {

    public CodeResult toResult(Code code) {
        if (code == null) {
            return null;
        }
        return new CodeResult(
                code.getCodeId(),
                code.getUpperCode() != null ? code.getUpperCode().getCodeId() : null,
                code.getCode(),
                code.getCodeName(),
                code.getDescription(),
                code.getSrt(),
                code.getOrderPath(),
                code.getEtc1(),
                code.getEtc2(),
                code.getEtc3(),
                code.getEtc4(),
                code.getUseYn(),
                calculateDepth(code),
                buildPath(code)
        );
    }

    private Integer calculateDepth(Code code) {
        int depth = 0;
        Code current = code.getUpperCode();
        while (current != null) {
            depth++;
            current = current.getUpperCode();
        }
        return depth;
    }

    private String buildPath(Code code) {
        List<String> segments = new ArrayList<>();
        Code current = code;
        while (current != null) {
            String name = current.getCodeName();
            if (name == null || name.isBlank()) {
                name = current.getCode();
            }
            segments.add(name);
            current = current.getUpperCode();
        }
        Collections.reverse(segments);
        int resolvedDepth = Math.max(segments.size() - 1, 0);
        return "[" + resolvedDepth + "] " + String.join(" > ", segments);
    }
}
