package com.base.contexts.identity.authn.application.usecase.port.in;

import com.base.contexts.identity.authn.application.usecase.dto.RefreshTokenCommand;
import com.base.contexts.identity.authn.application.usecase.result.AuthExecutionResult;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
