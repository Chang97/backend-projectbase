package com.base.authn.application.usecase.logout;

import com.base.authn.application.usecase.command.LogoutCommand;
import com.base.authn.application.usecase.result.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);
}
