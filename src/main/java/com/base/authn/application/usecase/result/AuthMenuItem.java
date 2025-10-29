package com.base.authn.application.usecase.result;

public record AuthMenuItem(
        Long menuId,
        String menuCode,
        String menuName,
        String url
) {
}
