package com.base.application.role.command;

import com.base.domain.role.Role;

public interface RoleCommandService {

    Role createRole(Role role);
    Role updateRole(Long id, Role role);
    void deleteRole(Long id);

    
}
