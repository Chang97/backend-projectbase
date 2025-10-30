package com.base.contexts.authr.permission.adapter.in.web.command.dto;

public record PermissionCommandRequest(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {}
