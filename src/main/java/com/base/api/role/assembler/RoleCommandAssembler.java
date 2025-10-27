package com.base.api.role.assembler;

import org.springframework.stereotype.Component;

import com.base.api.role.dto.RoleRequest;
import com.base.application.role.usecase.command.CreateRoleCommand;
import com.base.application.role.usecase.command.UpdateRoleCommand;

@Component
public class RoleCommandAssembler {

    public CreateRoleCommand toCreateCommand(RoleRequest request) {
        return new CreateRoleCommand(
                request.roleName(),
                request.useYn(),
                request.permissionIds()
        );
    }

    public UpdateRoleCommand toUpdateCommand(RoleRequest request) {
        return new UpdateRoleCommand(
                request.roleName(),
                request.useYn(),
                request.permissionIds()
        );
    }
}
