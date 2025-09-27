package com.base.application.permission.command;

import com.base.domain.permission.Permission;

public interface PermissionCommandService {

    Permission createPermission(Permission permission);
    Permission updatePermission(Long id, Permission permission);
    void deletePermission(Long id);
    
}
