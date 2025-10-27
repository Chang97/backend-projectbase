package com.base.identity.user.application.port.in.command;

import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.application.command.dto.UserResult;

public interface CreateUserUseCase {

    UserResult handle(CreateUserCommand command);
}
