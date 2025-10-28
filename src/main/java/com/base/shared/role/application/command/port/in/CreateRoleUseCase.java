package com.base.shared.role.application.command.port.in;

import com.base.shared.role.application.command.dto.RoleCommand;
import com.base.shared.role.application.command.dto.RoleCommandResult;

public interface CreateRoleUseCase {

    RoleCommandResult handle(RoleCommand command);
}
