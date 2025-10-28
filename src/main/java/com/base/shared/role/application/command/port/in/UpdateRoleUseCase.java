package com.base.shared.role.application.command.port.in;

import com.base.shared.role.application.command.dto.RoleCommand;
import com.base.shared.role.application.command.dto.RoleCommandResult;

public interface UpdateRoleUseCase {

    RoleCommandResult handle(Long roleId, RoleCommand command);
}
