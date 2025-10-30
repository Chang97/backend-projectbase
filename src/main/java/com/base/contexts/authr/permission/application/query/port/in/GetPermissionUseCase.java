package com.base.contexts.authr.permission.application.query.port.in;

import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;

public interface GetPermissionUseCase {

    PermissionQueryResult handle(Long permissionId);
}
