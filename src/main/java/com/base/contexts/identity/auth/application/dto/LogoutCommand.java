package com.base.contexts.identity.auth.application.dto;

public record LogoutCommand(
        String refreshToken
) {
}
