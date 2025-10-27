package com.base.shared.code.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;


import com.base.shared.code.adapter.in.web.command.dto.CodeCommandRequest;
import com.base.shared.code.application.command.dto.CodeCommand;


@Component
public class CodeCommandRequestMapper {

    public CodeCommand toCommand(CodeCommandRequest request) {
        return new CodeCommand(
                request.upperCodeId(),
                request.code(),
                request.codeName(),
                request.description(),
                request.srt(),
                request.etc1(),
                request.etc2(),
                request.etc3(),
                request.etc4(),
                request.useYn()
        );
    }

}
