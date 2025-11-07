package com.base.contexts.authr.role.application.command.handler;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.port.out.PermissionCommandPort;
import com.base.contexts.authr.role.application.command.dto.RoleCommand;
import com.base.contexts.authr.role.application.command.dto.RoleCommandResult;
import com.base.contexts.authr.role.application.command.mapper.RoleCommandMapper;
import com.base.contexts.authr.role.application.command.port.in.CreateRoleUseCase;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.port.out.RoleCommandPort;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateRoleHandler implements CreateRoleUseCase {

    private final RoleCommandPort roleCommandPort;
    private final PermissionCommandPort permissionCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final RoleCommandMapper roleCommandMapper;

    @Override
    public RoleCommandResult handle(RoleCommand command) {
        Role role = roleCommandMapper.toDomain(command);
        if (roleCommandPort.existsByName(role.getRoleName())) {
            throw new ConflictException("Role name already exists: " + role.getRoleName());
        }

        Role saved = roleCommandPort.save(role);
        syncRolePermissions(saved, command.permissionIds());
        List<Long> affectedUsers = userRoleMapCommandPort.findUserIdsByRoleIds(List.of(saved.getRoleId().value()));
        authorityCacheEventPort.publishRoleAuthoritiesChanged(affectedUsers);
        authorityCacheEventPort.publishPermissionsChanged(affectedUsers);
        return roleCommandMapper.toResult(saved);
    }

    private void syncRolePermissions(Role role, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }

        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (sanitizedIds.isEmpty()) {
            rolePermissionMapCommandPort.deleteAllByRoleId(role.getRoleId().value());
            return;
        }

        List<Permission> permissions = permissionCommandPort.findAllByIds(sanitizedIds);

        if (permissions.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        rolePermissionMapCommandPort.deleteAllByRoleId(role.getRoleId().value());

        List<RolePermissionMap> assignments = sanitizedIds.stream()
                .map(permissionId -> RolePermissionMap.of(role.getRoleId().value(), permissionId))
                .toList();

        rolePermissionMapCommandPort.saveAll(assignments);
    }
}
