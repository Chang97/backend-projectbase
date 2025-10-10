package com.base.api.auth.dto;

import java.time.OffsetDateTime;

import com.base.api.user.dto.UserResponse;

public record LoginResponse(
        String accessToken,
        String tokenType,
        OffsetDateTime expiresAt,
        UserResponse user
) {
}
