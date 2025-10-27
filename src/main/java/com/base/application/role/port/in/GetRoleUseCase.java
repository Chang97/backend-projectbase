package com.base.application.role.port.in;

import com.base.application.role.usecase.result.RoleResult;

public interface GetRoleUseCase {

    RoleResult handle(Long roleId);
}
