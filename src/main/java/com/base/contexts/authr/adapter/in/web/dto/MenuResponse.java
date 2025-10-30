package com.base.contexts.authr.adapter.in.web.dto;

import java.util.List;

public record MenuResponse(
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
) {
}
