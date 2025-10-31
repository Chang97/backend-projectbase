package com.base.contexts.authr.role.application.command.handler;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.port.out.PermissionRepository;
import com.base.contexts.authr.role.application.command.dto.RoleCommand;
import com.base.contexts.authr.role.application.command.dto.RoleCommandResult;
import com.base.contexts.authr.role.application.command.mapper.RoleCommandMapper;
import com.base.contexts.authr.role.application.command.port.in.UpdateRoleUseCase;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.port.out.RoleRepository;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateRoleHandler implements UpdateRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final RoleCommandMapper roleCommandMapper;

    @Override
    public RoleCommandResult handle(Long roleId, RoleCommand command) {
        Role existing = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        String newName = command.roleName();
        if (newName != null && !newName.equals(existing.getRoleName())
                && roleRepository.existsByName(newName)) {
            throw new ConflictException("Role name already exists: " + newName);
        }

        roleCommandMapper.apply(existing, command);
        Role saved = roleRepository.save(existing);
        syncRolePermissions(saved, command.permissionIds());
        List<Long> affectedUsers = userRoleMapRepository.findUserIdsByRoleIds(List.of(saved.getRoleId().value()));
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
            rolePermissionMapRepository.deleteAllByRoleId(role.getRoleId().value());
            return;
        }

        List<Permission> permissions = permissionRepository.findAllByIds(sanitizedIds);

        if (permissions.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        rolePermissionMapRepository.deleteAllByRoleId(role.getRoleId().value());

        List<RolePermissionMap> assignments = sanitizedIds.stream()
                .map(permissionId -> RolePermissionMap.of(role.getRoleId().value(), permissionId))
                .toList();

        rolePermissionMapRepository.saveAll(assignments);
    }
}
