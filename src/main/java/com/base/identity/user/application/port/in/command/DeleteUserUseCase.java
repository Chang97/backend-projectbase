package com.base.identity.user.application.port.in.command;

public interface DeleteUserUseCase {

    void handle(Long userId);
}
