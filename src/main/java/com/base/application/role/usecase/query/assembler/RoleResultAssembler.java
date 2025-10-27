package com.base.application.role.usecase.query.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.role.usecase.result.RoleResult;
import com.base.domain.role.Role;

@Component
public class RoleResultAssembler {

    public RoleResult toResult(Role role, List<Long> permissionIds) {
        return new RoleResult(
                role.getRoleId(),
                role.getRoleName(),
                role.getUseYn(),
                permissionIds
        );
    }
}
