package com.base.shared.role.application.query.port.in;

import com.base.shared.role.application.query.dto.RoleQueryResult;

public interface GetRoleUseCase {

    RoleQueryResult handle(Long roleId);
}
