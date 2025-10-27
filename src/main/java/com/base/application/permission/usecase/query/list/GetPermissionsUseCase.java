package com.base.application.permission.usecase.query.list;

import java.util.List;

import com.base.application.permission.usecase.query.condition.PermissionSearchCondition;
import com.base.application.permission.usecase.result.PermissionResult;

public interface GetPermissionsUseCase {

    List<PermissionResult> handle(PermissionSearchCondition condition);
}
