package com.base.contexts.authr.role.application.query.port.in;

import com.base.contexts.authr.role.application.query.dto.RoleQueryResult;

public interface GetRoleUseCase {

    RoleQueryResult handle(Long roleId);
}
