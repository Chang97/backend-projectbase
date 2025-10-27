package com.base.application.auth.usecase.logout;

import com.base.application.auth.usecase.command.LogoutCommand;
import com.base.application.auth.usecase.result.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);
}
