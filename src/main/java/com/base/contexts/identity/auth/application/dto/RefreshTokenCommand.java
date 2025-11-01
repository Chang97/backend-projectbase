package com.base.contexts.identity.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(
        @NotBlank(message = "Refresh token is required.")
        String refreshToken
) {
}
