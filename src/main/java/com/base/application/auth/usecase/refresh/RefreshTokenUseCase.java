package com.base.application.auth.usecase.refresh;

import com.base.application.auth.usecase.command.RefreshTokenCommand;
import com.base.application.auth.usecase.result.AuthExecutionResult;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
