package com.base.shared.role.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.base.shared.core.util.StringNormalizer;
import java.util.List;
import java.util.Objects;

import com.base.shared.role.adapter.in.web.command.dto.RoleCommandRequest;
import com.base.shared.role.adapter.in.web.command.dto.RoleCommandResponse;
import com.base.shared.role.application.command.dto.RoleCommand;
import com.base.shared.role.application.command.dto.RoleCommandResult;

@Component
public class RoleCommandWebMapper {

    public RoleCommand toCommand(RoleCommandRequest request) {
        return new RoleCommand(
                StringNormalizer.trimToNull(request.roleName()),
                request.useYn(),
                sanitizePermissionIds(request.permissionIds())
        );
    }

    public RoleCommandResponse toResponse(RoleCommandResult result) {
        return new RoleCommandResponse(result.roleId());
    }

    private List<Long> sanitizePermissionIds(List<Long> permissionIds) {
        if (permissionIds == null) {
            return List.of();
        }
        return permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }
}
