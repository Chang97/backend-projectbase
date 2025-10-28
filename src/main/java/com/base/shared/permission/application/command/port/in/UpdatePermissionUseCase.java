package com.base.shared.permission.application.command.port.in;

import com.base.shared.permission.application.command.dto.PermissionCommand;
import com.base.shared.permission.application.command.dto.PermissionCommandResult;

public interface UpdatePermissionUseCase {

    PermissionCommandResult handle(Long permissionId, PermissionCommand command);
}
