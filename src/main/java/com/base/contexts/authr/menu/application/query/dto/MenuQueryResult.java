package com.base.contexts.authr.menu.application.query.dto;

import java.util.List;

public record MenuQueryResult(
    Long menuId,
    String menuCode,
    Long upperMenuId,
    String menuName,
    String menuCn,
    String url,
    Integer srt,
    Boolean useYn,
    Integer depth,
    String path,
    List<Long> permissionIds
) {}
