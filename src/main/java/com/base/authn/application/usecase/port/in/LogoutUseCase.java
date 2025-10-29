package com.base.authn.application.usecase.port.in;

import com.base.authn.application.usecase.dto.LogoutCommand;
import com.base.authn.application.usecase.result.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);

}
