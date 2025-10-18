package com.base.api.user.dto;

public record PasswordChangeRequest(
        String currentPassword,
        String newPassword
) {
}
