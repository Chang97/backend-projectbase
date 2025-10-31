package com.base.contexts.code.application.command.port.in;

import com.base.contexts.code.application.command.dto.CodeCommand;
import com.base.contexts.code.application.command.dto.CodeCommandResult;

public interface UpdateCodeUseCase {

    CodeCommandResult handle(Long codeId, CodeCommand command);
}
