package com.base.contexts.identity.authn.application.usecase.port.in;

import com.base.contexts.identity.authn.application.usecase.dto.LogoutCommand;
import com.base.contexts.identity.authn.application.usecase.result.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);

}
