package com.base.application.permission.result;

public record PermissionResult(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
