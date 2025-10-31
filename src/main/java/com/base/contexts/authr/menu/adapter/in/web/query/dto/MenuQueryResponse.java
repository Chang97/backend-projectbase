package com.base.contexts.authr.menu.adapter.in.web.query.dto;

import java.util.List;

public record MenuQueryResponse(
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
