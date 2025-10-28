package com.base.shared.permission.adapter.in.web.query.dto;

public record PermissionQueryRequest(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
