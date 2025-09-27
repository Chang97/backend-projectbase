package com.base.application.role.command;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;

public interface RoleCommandService {

    RoleResponse createRole(RoleRequest role);
    RoleResponse updateRole(Long id, RoleRequest role);
    void deleteRole(Long id);

    
}
