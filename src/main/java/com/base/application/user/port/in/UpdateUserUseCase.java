package com.base.application.user.port.in;

import com.base.application.user.usecase.command.UpdateUserCommand;
import com.base.application.user.usecase.result.UserResult;

public interface UpdateUserUseCase {

    UserResult handle(Long userId, UpdateUserCommand command);
}
