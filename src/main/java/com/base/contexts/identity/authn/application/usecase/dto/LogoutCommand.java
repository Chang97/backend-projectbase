package com.base.contexts.identity.authn.application.usecase.dto;

public record LogoutCommand(
        String refreshToken
) {
}
