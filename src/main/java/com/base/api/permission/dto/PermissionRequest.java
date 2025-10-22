package com.base.api.permission.dto;

public record PermissionRequest(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
