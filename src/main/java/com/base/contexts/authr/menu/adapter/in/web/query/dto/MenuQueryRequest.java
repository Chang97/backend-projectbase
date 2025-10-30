package com.base.contexts.authr.menu.adapter.in.web.query.dto;

public record MenuQueryRequest(
        Long menuId,
        Long upperMenuId,
        String menuCode,
        String menuName,
        Boolean useYn
) {}
