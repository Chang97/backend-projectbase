package com.base.identity.user.application.command.port.in;

public interface DeleteUserUseCase {

    void handle(Long userId);
}
