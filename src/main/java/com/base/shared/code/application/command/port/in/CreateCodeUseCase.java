package com.base.shared.code.application.command.port.in;

import com.base.shared.code.application.command.dto.CodeCommandResult;
import com.base.shared.code.application.command.dto.CodeCommand;

public interface CreateCodeUseCase {

    CodeCommandResult handle(CodeCommand command);
}
