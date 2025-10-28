package com.base.shared.menu.domain.model;

public record MenuFilter(
        Long menuId,
        Long upperMenuId,
        String menuCode,
        String menuName,
        Boolean useYn
) {
    public static MenuFilter empty() {
        return new MenuFilter(null, null, null, null, null);
    }
}
