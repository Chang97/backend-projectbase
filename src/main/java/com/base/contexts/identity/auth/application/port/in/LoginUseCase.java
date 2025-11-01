package com.base.contexts.identity.auth.application.port.in;

import com.base.contexts.identity.auth.application.dto.AuthExecutionResult;
import com.base.contexts.identity.auth.application.dto.LoginCommand;

public interface LoginUseCase {

    AuthExecutionResult handle(LoginCommand command);

}
