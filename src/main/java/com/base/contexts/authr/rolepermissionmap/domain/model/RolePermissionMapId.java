package com.base.contexts.authr.rolepermissionmap.domain.model;

public record RolePermissionMapId(Long roleId, Long permissionId) {

    public static RolePermissionMapId of(Long roleId, Long permissionId) {
        return new RolePermissionMapId(roleId, permissionId);
    }
}
