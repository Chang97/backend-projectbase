package com.base.application.code.port.in;

import com.base.application.code.usecase.command.UpdateCodeCommand;
import com.base.application.code.usecase.result.CodeResult;

public interface UpdateCodeUseCase {

    CodeResult handle(Long codeId, UpdateCodeCommand command);
}
