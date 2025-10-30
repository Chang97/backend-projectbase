package com.base.contexts.authr.permission.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.permission.application.command.dto.PermissionCommand;
import com.base.contexts.authr.permission.application.command.dto.PermissionCommandResult;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.model.PermissionId;

@Component
public class PermissionCommandMapper {
    public Permission toDomain(PermissionCommand command) {
        boolean useYn = Boolean.TRUE.equals(command.useYn());

        return Permission.create(
                command.permissionCode(),
                command.permissionName(),
                useYn
        );
    }

    public Permission toDomain(Long permissionId, PermissionCommand command) {
        boolean useYn = Boolean.TRUE.equals(command.useYn());

        return Permission.restore(
                PermissionId.of(permissionId),
                command.permissionCode(),
                command.permissionName(),
                useYn
        );
    }

    public PermissionCommandResult toResult(Permission domain) {
        Long id = domain.getPermissionId() != null ? domain.getPermissionId().permissionId() : null;
        return new PermissionCommandResult(id);
    }
}
