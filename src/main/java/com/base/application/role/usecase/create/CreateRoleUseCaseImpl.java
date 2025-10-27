package com.base.application.role.usecase.create;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.permission.port.out.PermissionLookupPort;
import com.base.application.role.port.in.CreateRoleUseCase;
import com.base.application.role.port.out.RolePersistencePort;
import com.base.application.role.port.out.RolePermissionPort;
import com.base.application.role.port.out.UserRoleAssociationPort;
import com.base.application.role.usecase.command.CreateRoleCommand;
import com.base.application.role.usecase.query.assembler.RoleResultAssembler;
import com.base.application.role.usecase.result.RoleResult;
import com.base.domain.permission.Permission;
import com.base.domain.role.Role;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateRoleUseCaseImpl implements CreateRoleUseCase {

    private final RolePersistencePort rolePersistencePort;
    private final PermissionLookupPort permissionLookupPort;
    private final RolePermissionPort rolePermissionPort;
    private final UserRoleAssociationPort userRoleAssociationPort;
    private final CacheInvalidationEventPublisher cachePublisher;
    private final RoleResultAssembler roleResultAssembler;

    @Override
    public RoleResult handle(CreateRoleCommand command) {
        if (rolePersistencePort.existsByRoleName(command.roleName())) {
            throw new ConflictException("Role name already exists: " + command.roleName());
        }

        Role role = Role.builder()
                .roleName(command.roleName())
                .useYn(Boolean.TRUE.equals(command.useYn()))
                .build();

        Role saved = rolePersistencePort.save(role);
        syncRolePermissions(saved, command.permissionIds());

        List<Long> permissionIds = rolePermissionPort.findPermissionIdsByRoleId(saved.getRoleId());
        cachePublisher.publishRoleAuthorityChanged(userRoleAssociationPort.findUserIdsByRoleIds(List.of(saved.getRoleId())));

        return roleResultAssembler.toResult(saved, permissionIds);
    }

    private void syncRolePermissions(Role role, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }
        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        rolePermissionPort.clearPermissions(role.getRoleId());

        if (sanitizedIds.isEmpty()) {
            return;
        }

        Map<Long, Permission> permissionMap = permissionLookupPort.findAllByIds(sanitizedIds).stream()
                .collect(Collectors.toMap(Permission::getPermissionId, Function.identity()));

        if (permissionMap.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        rolePermissionPort.assignPermissions(
                role,
                sanitizedIds.stream()
                        .map(permissionMap::get)
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
