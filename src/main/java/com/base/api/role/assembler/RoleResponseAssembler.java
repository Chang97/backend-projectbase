package com.base.api.role.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.role.dto.RoleResponse;
import com.base.application.role.usecase.result.RoleResult;

@Component
public class RoleResponseAssembler {

    public RoleResponse toResponse(RoleResult result) {
        return new RoleResponse(
                result.roleId(),
                result.roleName(),
                result.useYn(),
                result.permissionIds()
        );
    }

    public List<RoleResponse> toResponses(List<RoleResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
