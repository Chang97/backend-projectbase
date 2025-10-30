package com.base.contexts.authr.permission.domain.model;

public record PermissionId(Long permissionId) {

    public static PermissionId of(Long permissionId) {
        return permissionId == null ? null : new PermissionId(permissionId);
    }
}
