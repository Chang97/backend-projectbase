package com.base.application.permission.command;

public record UpdatePermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
