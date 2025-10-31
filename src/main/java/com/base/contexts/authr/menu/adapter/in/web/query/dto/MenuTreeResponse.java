package com.base.contexts.authr.menu.adapter.in.web.query.dto;

import java.util.List;

public record MenuTreeResponse(
        Long menuId,
        String menuCode,
        String menuName,
        String url,
        List<MenuTreeResponse> children
) {
}
