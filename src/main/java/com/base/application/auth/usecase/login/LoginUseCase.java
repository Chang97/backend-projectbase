package com.base.application.auth.usecase.login;

import com.base.application.auth.usecase.command.LoginCommand;
import com.base.application.auth.usecase.result.AuthExecutionResult;

public interface LoginUseCase {

    AuthExecutionResult handle(LoginCommand command);

}
