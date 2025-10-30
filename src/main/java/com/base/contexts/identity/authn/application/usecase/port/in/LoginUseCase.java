package com.base.contexts.identity.authn.application.usecase.port.in;

import com.base.contexts.identity.authn.application.usecase.dto.LoginCommand;
import com.base.contexts.identity.authn.application.usecase.result.AuthExecutionResult;

public interface LoginUseCase {

    AuthExecutionResult handle(LoginCommand command);

}
