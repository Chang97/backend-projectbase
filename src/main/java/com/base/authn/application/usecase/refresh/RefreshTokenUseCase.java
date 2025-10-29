package com.base.authn.application.usecase.refresh;

import com.base.authn.application.usecase.command.RefreshTokenCommand;
import com.base.authn.application.usecase.result.AuthExecutionResult;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
