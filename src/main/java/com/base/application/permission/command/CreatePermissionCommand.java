package com.base.application.permission.command;

public record CreatePermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
