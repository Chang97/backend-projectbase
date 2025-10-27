package com.base.identity.user.adapter.in.web.dto;

public record PasswordChangeRequest(
        String currentPassword,
        String newPassword
) {
}
