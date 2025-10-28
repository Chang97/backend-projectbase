package com.base.shared.menu.adapter.in.web.query.dto;

import java.util.List;

public record MenuQueryResponse(
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
