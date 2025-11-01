package com.base.contexts.identity.auth.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Login ID is required.")
        String loginId,
        @NotBlank(message = "Password is required.")
        String password
) {
}
