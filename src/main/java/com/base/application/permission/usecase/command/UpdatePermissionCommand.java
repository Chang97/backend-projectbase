package com.base.application.permission.usecase.command;

public record UpdatePermissionCommand(
        String permissionCode,
        String permissionName,
        Boolean useYn
) {
}
