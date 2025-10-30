package com.base.contexts.authr.code.application.command.port.in;

import com.base.contexts.authr.code.application.command.dto.CodeCommand;
import com.base.contexts.authr.code.application.command.dto.CodeCommandResult;

public interface CreateCodeUseCase {

    CodeCommandResult handle(CodeCommand command);
}
