package com.base.shared.menu.application.query.dto;

public record MenuQuery(
        Long menuId,
        Long upperMenuId,
        String menuCode,
        String menuName,
        Boolean useYn
) {}
