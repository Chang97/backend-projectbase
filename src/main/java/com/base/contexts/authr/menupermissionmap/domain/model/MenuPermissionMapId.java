package com.base.contexts.authr.menupermissionmap.domain.model;

public record MenuPermissionMapId(Long menuId, Long permissionId) {

    public static MenuPermissionMapId of(Long menuId, Long permissionId) {
        return new MenuPermissionMapId(menuId, permissionId);
    }
}
