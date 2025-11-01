package com.base.contexts.identity.auth.application.port.in;

import com.base.contexts.identity.auth.application.dto.LogoutCommand;
import com.base.contexts.identity.auth.application.dto.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);

}
