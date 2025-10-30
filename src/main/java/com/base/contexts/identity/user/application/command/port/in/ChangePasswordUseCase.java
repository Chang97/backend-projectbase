package com.base.contexts.identity.user.application.command.port.in;

import com.base.contexts.identity.user.application.command.dto.UserPasswordCommand;

public interface ChangePasswordUseCase {

    void handle(UserPasswordCommand command);
}
