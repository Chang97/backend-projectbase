package com.base.application.auth.usecase.command;

public record LogoutCommand(
        String refreshToken
) {
}
