package com.base.authn.application.usecase.command;

public record LogoutCommand(
        String refreshToken
) {
}
