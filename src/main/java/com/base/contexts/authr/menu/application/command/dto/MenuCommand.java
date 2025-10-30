package com.base.contexts.authr.menu.application.command.dto;

import java.util.List;

public record MenuCommand(
        Long upperMenuId,
        String menuCode,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<Long> permissionIds
) {}
