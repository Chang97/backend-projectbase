package com.base.contexts.authr.role.application.command.port.in;

import com.base.contexts.authr.role.application.command.dto.RoleCommand;
import com.base.contexts.authr.role.application.command.dto.RoleCommandResult;

public interface CreateRoleUseCase {

    RoleCommandResult handle(RoleCommand command);
}
