package com.base.application.permission.command;

import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;

public interface PermissionCommandService {

    PermissionResponse createPermission(PermissionRequest request);
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);
    
}
