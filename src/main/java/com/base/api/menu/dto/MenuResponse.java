// MenuResponse.java
package com.base.api.menu.dto;

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
        Integer lvl,
        String path,
        List<Long> permissionIds
) {}
