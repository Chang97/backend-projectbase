package com.base.application.menu.usecase.result;

import java.util.List;

public record MenuTreeNodeResult(
        Long menuId,
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        Integer depth,
        List<MenuTreeNodeResult> children
) {
}
