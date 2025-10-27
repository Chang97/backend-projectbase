package com.base.shared.code.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.base.shared.code.adapter.in.web.command.dto.CodeCommandResponse;
import com.base.shared.code.application.command.dto.CodeCommandResult;


@Component
public class CodeCommandResponseMapper {

    public CodeCommandResponse toResponse(CodeCommandResult result) {
        return new CodeCommandResponse(result.codeId());
    }

}
