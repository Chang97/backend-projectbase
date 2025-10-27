package com.base.application.permission.usecase.query.list;

import java.util.List;

import com.base.application.permission.result.PermissionResult;
import com.base.application.permission.usecase.query.condition.PermissionSearchCondition;

public interface GetPermissionsUseCase {

    List<PermissionResult> handle(PermissionSearchCondition condition);
}
