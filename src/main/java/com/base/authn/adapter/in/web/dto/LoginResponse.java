package com.base.authn.adapter.in.web.dto;

import java.util.List;

public record LoginResponse(
        UserSummaryResponse user,
        List<MenuTreeResponse> menus,
        List<MenuResponse> accessibleMenus,
        List<String> permissions
) {
}
