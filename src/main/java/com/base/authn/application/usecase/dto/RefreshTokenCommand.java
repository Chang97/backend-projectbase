package com.base.authn.application.usecase.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(
        @NotBlank(message = "Refresh token is required.")
        String refreshToken
) {
}
