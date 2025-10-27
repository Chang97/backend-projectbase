package com.base.application.permission.usecase.update;

import com.base.application.permission.usecase.command.UpdatePermissionCommand;
import com.base.application.permission.usecase.result.PermissionResult;

public interface UpdatePermissionUseCase {

    PermissionResult handle(Long permissionId, UpdatePermissionCommand command);
}
