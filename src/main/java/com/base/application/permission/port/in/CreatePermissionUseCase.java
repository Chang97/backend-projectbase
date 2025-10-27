package com.base.application.permission.port.in;

import com.base.application.permission.command.CreatePermissionCommand;
import com.base.application.permission.result.PermissionResult;

public interface CreatePermissionUseCase {

    PermissionResult handle(CreatePermissionCommand command);
}
