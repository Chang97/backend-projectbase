package com.base.shared.permission.application.query.dto;

public record PermissionQueryResult(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
