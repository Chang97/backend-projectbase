package com.base.application.user.port.in;

import com.base.application.user.usecase.command.CreateUserCommand;
import com.base.application.user.usecase.result.UserResult;

public interface CreateUserUseCase {

    UserResult handle(CreateUserCommand command);
}
