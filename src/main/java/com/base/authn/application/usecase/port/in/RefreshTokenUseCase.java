package com.base.authn.application.usecase.port.in;

import com.base.authn.application.usecase.dto.RefreshTokenCommand;
import com.base.authn.application.usecase.result.AuthExecutionResult;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
