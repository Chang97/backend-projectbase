package com.base.application.permission.usecase.create;

import com.base.application.permission.usecase.command.CreatePermissionCommand;
import com.base.application.permission.usecase.result.PermissionResult;

public interface CreatePermissionUseCase {

    PermissionResult handle(CreatePermissionCommand command);
}
