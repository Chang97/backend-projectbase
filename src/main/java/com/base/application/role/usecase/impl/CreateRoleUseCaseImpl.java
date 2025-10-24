package com.base.application.role.usecase.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.role.usecase.CreateRoleUseCase;
import com.base.application.role.usecase.command.CreateRoleCommand;
import com.base.application.role.usecase.result.RoleResult;
import com.base.domain.mapping.RolePermissionMap;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateRoleUseCaseImpl implements CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final CacheInvalidationEventPublisher cachePublisher;

    @Override
    public RoleResult handle(CreateRoleCommand command) {
        if (roleRepository.existsByRoleName(command.roleName())) {
            throw new ConflictException("Role name already exists: " + command.roleName());
        }

        Role role = Role.builder()
                .roleName(command.roleName())
                .useYn(Boolean.TRUE.equals(command.useYn()))
                .build();

        Role saved = roleRepository.save(role);
        syncRolePermissions(saved, command.permissionIds());

        List<Long> permissionIds = rolePermissionMapRepository.findPermissionIdsByRoleId(saved.getRoleId());
        cachePublisher.publishRoleAuthorityChanged(userRoleMapRepository.findUserIdsByRoleIds(List.of(saved.getRoleId())));

        return new RoleResult(
                saved.getRoleId(),
                saved.getRoleName(),
                saved.getUseYn(),
                permissionIds
        );
    }

    private void syncRolePermissions(Role role, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }
        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        rolePermissionMapRepository.deleteByRoleRoleId(role.getRoleId());

        if (sanitizedIds.isEmpty()) {
            return;
        }

        Map<Long, Permission> permissionMap = permissionRepository.findAllById(sanitizedIds).stream()
                .collect(Collectors.toMap(Permission::getPermissionId, Function.identity()));

        if (permissionMap.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        rolePermissionMapRepository.saveAll(
                sanitizedIds.stream()
                        .map(id -> RolePermissionMap.builder()
                                .role(role)
                                .permission(permissionMap.get(id))
                                .build())
                        .toList()
        );
    }
}