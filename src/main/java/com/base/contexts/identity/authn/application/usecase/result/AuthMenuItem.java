package com.base.contexts.identity.authn.application.usecase.result;

public record AuthMenuItem(
        Long menuId,
        String menuCode,
        String menuName,
        String url
) {
}
