package com.base.application.role.port.in;

import com.base.application.role.usecase.command.UpdateRoleCommand;
import com.base.application.role.usecase.result.RoleResult;

public interface UpdateRoleUseCase {

    RoleResult handle(Long roleId, UpdateRoleCommand command);
}
