package com.base.identity.user.application.port.in.command;

import com.base.application.user.usecase.command.UpdateUserCommand;
import com.base.application.user.usecase.result.UserResult;

public interface UpdateUserUseCase {

    UserResult handle(Long userId, UpdateUserCommand command);
}
