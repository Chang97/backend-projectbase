package com.base.application.permission.usecase.result;

public record PermissionResult(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
