package com.base.contexts.authr.menu.application.query.dto;

import java.util.List;

public record MenuQueryResult(
        Long menuId,
        Long upperMenuId,
        String menuCode,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn,
        List<Long> permissionIds
) {}
