package com.base.authn.application.usecase.port.in;

import com.base.authn.application.usecase.dto.LoginCommand;
import com.base.authn.application.usecase.result.AuthExecutionResult;

public interface LoginUseCase {

    AuthExecutionResult handle(LoginCommand command);

}
