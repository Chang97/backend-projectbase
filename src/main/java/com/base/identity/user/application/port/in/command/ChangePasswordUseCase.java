package com.base.identity.user.application.port.in.command;

import com.base.application.user.usecase.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {

    void handle(ChangePasswordCommand command);
}
