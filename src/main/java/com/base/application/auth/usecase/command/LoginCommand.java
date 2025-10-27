package com.base.application.auth.usecase.command;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Login ID is required.")
        String loginId,
        @NotBlank(message = "Password is required.")
        String password
) {}
