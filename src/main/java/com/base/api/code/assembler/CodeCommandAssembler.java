package com.base.api.code.assembler;

import org.springframework.stereotype.Component;

import com.base.api.code.dto.CodeRequest;
import com.base.application.code.usecase.command.CreateCodeCommand;
import com.base.application.code.usecase.command.UpdateCodeCommand;

@Component
public class CodeCommandAssembler {

    public CreateCodeCommand toCreateCommand(CodeRequest request) {
        return new CreateCodeCommand(
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

    public UpdateCodeCommand toUpdateCommand(CodeRequest request) {
        return new UpdateCodeCommand(
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
