package com.base.application.permission.query;

import java.util.List;

import com.base.domain.permission.Permission;

public interface PermissionQueryService {

    List<Permission> getPermissions();
    Permission getPermission(Long id);

}
