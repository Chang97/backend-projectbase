package com.base.application.role.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.role.dto.RoleResponse;
import com.base.api.role.mapper.RoleMapper;
import com.base.domain.role.Role;

@Component
public class RoleResponseAssembler {

    private final RoleMapper roleMapper;

    public RoleResponseAssembler(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public RoleResponse assemble(Role role, List<Long> permissionIds) {
        RoleResponse base = roleMapper.toResponse(role);
        return new RoleResponse(
                base.roleId(),
                base.roleName(),
                base.useYn(),
                permissionIds
        );
    }
}
