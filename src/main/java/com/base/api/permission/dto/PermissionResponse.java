package com.base.api.permission.dto;

public record PermissionResponse(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
