package com.base.authr.adapter.in.web.dto;

import java.util.List;

public record MenuTreeResponse(
        Long menuId,
        String menuCode,
        String menuName,
        String url,
        List<MenuTreeResponse> children
) {
}
