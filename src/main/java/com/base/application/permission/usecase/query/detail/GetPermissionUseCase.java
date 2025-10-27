package com.base.application.permission.usecase.query.detail;

import com.base.application.permission.usecase.result.PermissionResult;

public interface GetPermissionUseCase {

    PermissionResult handle(Long permissionId);
}
