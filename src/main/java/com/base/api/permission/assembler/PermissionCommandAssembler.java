package com.base.api.permission.assembler;

import org.springframework.stereotype.Component;

import com.base.api.permission.dto.PermissionRequest;
import com.base.application.permission.usecase.command.CreatePermissionCommand;
import com.base.application.permission.usecase.command.UpdatePermissionCommand;

@Component
public class PermissionCommandAssembler {

    public CreatePermissionCommand toCreateCommand(PermissionRequest request) {
        return new CreatePermissionCommand(
                request.permissionCode(),
                request.permissionName(),
                request.useYn()
        );
    }

    public UpdatePermissionCommand toUpdateCommand(PermissionRequest request) {
        return new UpdatePermissionCommand(
                request.permissionCode(),
                request.permissionName(),
                request.useYn()
        );
    }
}
