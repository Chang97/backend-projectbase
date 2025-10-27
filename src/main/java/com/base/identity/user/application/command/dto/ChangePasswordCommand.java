package com.base.identity.user.application.command.dto;

public record ChangePasswordCommand(
        Long userId,
        String currentPassword,
        String newPassword
) {
}
