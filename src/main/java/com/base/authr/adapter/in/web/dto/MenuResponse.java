package com.base.authr.adapter.in.web.dto;

public record MenuResponse(
        Long menuId,
        String menuCode,
        String menuName,
        String url
) {
}
