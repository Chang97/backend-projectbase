package com.base.contexts.identity.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Login ID is required.")
        String loginId,
        @NotBlank(message = "Password is required.")
        String password
) {}
