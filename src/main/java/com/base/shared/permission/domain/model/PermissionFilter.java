package com.base.shared.permission.domain.model;

public record PermissionFilter(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
    public static PermissionFilter empty() {
        return new PermissionFilter(null, null, null, null);
    }
}
