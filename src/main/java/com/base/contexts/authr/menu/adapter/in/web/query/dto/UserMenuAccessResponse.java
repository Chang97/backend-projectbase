package com.base.contexts.authr.menu.adapter.in.web.query.dto;

import java.util.List;

public record UserMenuAccessResponse(
    List<MenuTreeResponse> menus,
    List<MenuQueryResponse> accessibleMenus
) {
}
