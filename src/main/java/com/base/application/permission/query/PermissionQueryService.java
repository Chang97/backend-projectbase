package com.base.application.permission.query;

import java.util.List;

import com.base.api.permission.dto.PermissionResponse;

public interface PermissionQueryService {

    PermissionResponse getPermission(Long id);
    List<PermissionResponse> getPermissions(PermissionSearchCondition condition);

}
