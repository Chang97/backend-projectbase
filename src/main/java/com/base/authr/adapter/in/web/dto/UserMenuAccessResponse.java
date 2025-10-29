package com.base.authr.adapter.in.web.dto;

import java.util.List;

public record UserMenuAccessResponse(
    List<MenuTreeResponse> menus,
    List<MenuResponse> accessibleMenus
) {
}
