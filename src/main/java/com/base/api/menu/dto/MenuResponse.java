// MenuResponse.java
package com.base.api.menu.dto;

public record MenuResponse(
        Long menuId,
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn
) {}
