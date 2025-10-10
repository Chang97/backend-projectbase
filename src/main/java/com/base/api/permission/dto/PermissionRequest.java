package com.base.api.permission.dto;

public record PermissionRequest(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
