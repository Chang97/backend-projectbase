package com.base.api.auth.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.dto.MenuTreeResponse;
import com.base.api.user.dto.UserResponse;

public record LoginResponse(
        String accessToken,
        String tokenType,
        OffsetDateTime expiresAt,
        UserResponse user,
        List<MenuTreeResponse> menus,
        List<MenuResponse> accessibleMenus
) {
}
