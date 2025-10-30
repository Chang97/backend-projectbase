package com.base.contexts.identity.user.application.command.port.in;

import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.application.command.dto.UserCommandResult;

public interface CreateUserUseCase {

    UserCommandResult handle(UserCommand command);
}
