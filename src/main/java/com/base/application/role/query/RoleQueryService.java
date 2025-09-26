package com.base.application.role.query;

import java.util.List;

import com.base.domain.role.Role;

public interface RoleQueryService {

    Role getRole(Long id);
    List<Role> getRoles();

}
