package com.base.contexts.authr.permission.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.permission.adapter.in.web.command.dto.PermissionCommandRequest;
import com.base.contexts.authr.permission.adapter.in.web.command.dto.PermissionCommandResponse;
import com.base.contexts.authr.permission.application.command.dto.PermissionCommand;
import com.base.contexts.authr.permission.application.command.dto.PermissionCommandResult;
import com.base.shared.core.util.StringNormalizer;

@Component
public class PermissionCommandWebMapper {

    public PermissionCommand toCommand(PermissionCommandRequest request) {
        return new PermissionCommand(
                StringNormalizer.trimToNull(request.permissionCode()),
                StringNormalizer.trimToNull(request.permissionName()),
                request.useYn()
        );
    }

    public PermissionCommandResponse toResponse(PermissionCommandResult result) {
        return new PermissionCommandResponse(result.permissionId());
    }
}
