package com.base.application.permission.usecase.command;

public record CreatePermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
