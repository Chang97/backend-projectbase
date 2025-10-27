package com.base.application.permission.port.in;

import com.base.application.permission.command.UpdatePermissionCommand;
import com.base.application.permission.result.PermissionResult;

public interface UpdatePermissionUseCase {

    PermissionResult handle(Long permissionId, UpdatePermissionCommand command);
}
