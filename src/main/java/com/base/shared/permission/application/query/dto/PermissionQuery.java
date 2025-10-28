package com.base.shared.permission.application.query.dto;

public record PermissionQuery(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
