package com.base.application.user.port.in;

import com.base.application.user.usecase.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {

    void handle(ChangePasswordCommand command);
}
