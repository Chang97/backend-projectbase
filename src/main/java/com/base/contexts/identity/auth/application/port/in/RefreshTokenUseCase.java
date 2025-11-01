package com.base.contexts.identity.auth.application.port.in;

import com.base.contexts.identity.auth.application.dto.AuthExecutionResult;
import com.base.contexts.identity.auth.application.dto.RefreshTokenCommand;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
