package com.base.authn.application.usecase.login;

import com.base.authn.application.usecase.command.LoginCommand;
import com.base.authn.application.usecase.result.AuthExecutionResult;

public interface LoginUseCase {

    AuthExecutionResult handle(LoginCommand command);

}
