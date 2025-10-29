package com.base.authr.application.dto;

import java.util.List;

public record MenuTreeResult(
    Long menuId,
    String menuCode,
    String menuName,
    String url,
    List<MenuTreeResult> children
) {}
