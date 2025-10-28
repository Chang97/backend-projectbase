package com.base.shared.permission.application.command.dto;

public record PermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
