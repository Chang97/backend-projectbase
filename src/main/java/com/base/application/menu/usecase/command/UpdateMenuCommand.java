package com.base.application.menu.usecase.command;

import java.util.List;

public record UpdateMenuCommand(
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<Long> permissionIds
) {
}
