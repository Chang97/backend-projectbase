package com.base.contexts.code.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.code.application.command.dto.CodeCommand;
import com.base.contexts.code.application.command.dto.CodeCommandResult;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.model.CodeId;

@Component
public class CodeCommandMapper {
    public Code toDomain(CodeCommand command) {
        boolean useYn = Boolean.TRUE.equals(command.useYn());
        CodeId upperCodeId = command.upperCodeId() != null
                ? CodeId.of(command.upperCodeId())
                : null;

        return Code.create(
                command.code(),
                command.codeName(),
                command.description(),
                command.srt(),
                useYn,
                command.etc1(),
                command.etc2(),
                command.etc3(),
                command.etc4(),
                upperCodeId
        );
    }

    public Code toDomain(Long codeId,  CodeCommand command) {
        boolean useYn = Boolean.TRUE.equals(command.useYn());
        CodeId upperCodeId = command.upperCodeId() != null
                ? CodeId.of(command.upperCodeId())
                : null;

        return Code.restore(
                new CodeId(codeId),
                upperCodeId,
                command.code(),
                command.codeName(),
                command.description(),
                command.srt(),
                null,
                useYn,
                command.etc1(),
                command.etc2(),
                command.etc3(),
                command.etc4()
        );
    }

    public CodeCommandResult toCommandResult(Code domain) {
        Long id = domain.getCodeId() != null ? domain.getCodeId().codeId() : null;
        return new CodeCommandResult(id);
    }
}
