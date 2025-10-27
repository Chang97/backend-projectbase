package com.base.application.user.usecase.command;

public record ChangePasswordCommand(
        Long userId,
        String currentPassword,
        String newPassword
) {
}
