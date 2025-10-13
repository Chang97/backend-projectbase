package com.base.api.auth.dto;

import java.util.List;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.dto.MenuTreeResponse;
import com.base.api.user.dto.UserResponse;

public record LoginResponse(
        UserResponse user,
        List<MenuTreeResponse> menus,
        List<MenuResponse> accessibleMenus
) {
}
