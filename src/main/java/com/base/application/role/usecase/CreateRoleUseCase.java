package com.base.application.role.usecase;

import com.base.application.role.usecase.command.CreateRoleCommand;
import com.base.application.role.usecase.result.RoleResult;

public interface CreateRoleUseCase {
    RoleResult handle(CreateRoleCommand command);
}