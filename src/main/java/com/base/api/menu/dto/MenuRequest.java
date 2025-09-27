// MenuRequest.java
package com.base.api.menu.dto;

public record MenuRequest(
        String menuCode,
        Long upperMenuId,
        String menuName,
        String menuCn,
        String url,
        Integer srt,
        Boolean useYn
) {}
