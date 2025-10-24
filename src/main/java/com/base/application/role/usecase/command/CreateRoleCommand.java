package com.base.application.role.usecase.command;

import java.util.List;

public record CreateRoleCommand(
    String roleName,
    Boolean useYn,
    List<Long> permissionIds
) {}