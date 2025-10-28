package com.base.shared.permission.application.query.port.in;

import com.base.shared.permission.application.query.dto.PermissionQueryResult;

public interface GetPermissionUseCase {

    PermissionQueryResult handle(Long permissionId);
}
