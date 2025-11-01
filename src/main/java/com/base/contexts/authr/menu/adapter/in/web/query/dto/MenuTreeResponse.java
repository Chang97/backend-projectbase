package com.base.contexts.authr.menu.adapter.in.web.query.dto;

import java.util.List;

public record MenuTreeResponse(
        Long menuId,
        Long upperMenuId,
        String menuCode,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        Integer lvl,
        List<MenuTreeResponse> children
) {
}
