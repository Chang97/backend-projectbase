package com.base.contexts.authr.permission.application.command.port.in;

import com.base.contexts.authr.permission.application.command.dto.PermissionCommand;
import com.base.contexts.authr.permission.application.command.dto.PermissionCommandResult;

public interface UpdatePermissionUseCase {

    PermissionCommandResult handle(Long permissionId, PermissionCommand command);
}
