package com.base.authn.application.usecase.dto;

public record LogoutCommand(
        String refreshToken
) {
}
