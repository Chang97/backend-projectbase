package com.base.shared.permission.application.query.port.in;

import java.util.List;

import com.base.shared.permission.application.query.dto.PermissionQuery;
import com.base.shared.permission.application.query.dto.PermissionQueryResult;

public interface GetPermissionsUseCase {

    List<PermissionQueryResult> handle(PermissionQuery query);
}
