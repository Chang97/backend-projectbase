package com.base.contexts.authr.permission.adapter.in.web.query.dto;

public record PermissionQueryResponse(
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
