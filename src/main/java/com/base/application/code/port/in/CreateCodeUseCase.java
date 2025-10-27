package com.base.application.code.port.in;

import com.base.application.code.usecase.command.CreateCodeCommand;
import com.base.application.code.usecase.result.CodeResult;

public interface CreateCodeUseCase {

    CodeResult handle(CreateCodeCommand command);
}
