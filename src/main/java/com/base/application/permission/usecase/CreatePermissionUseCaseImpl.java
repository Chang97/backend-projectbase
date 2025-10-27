package com.base.application.permission.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.permission.command.CreatePermissionCommand;
import com.base.application.permission.port.in.CreatePermissionUseCase;
import com.base.application.permission.result.PermissionResult;
import com.base.application.permission.usecase.query.assembler.PermissionResultAssembler;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreatePermissionUseCaseImpl implements CreatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;
    private final PermissionResultAssembler permissionResultAssembler;

    @Override
    public PermissionResult handle(CreatePermissionCommand command) {
        if (permissionRepository.existsByPermissionCode(command.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + command.permissionCode());
        }

        Permission permission = Permission.builder()
                .permissionCode(command.permissionCode())
                .permissionName(command.permissionName())
                .build();
        permission.setUseYn(command.useYn() == null ? Boolean.TRUE : command.useYn());

        Permission saved = permissionRepository.save(permission);
        evictAuthorityCacheForPermission(saved.getPermissionId());
        return permissionResultAssembler.toResult(saved);
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(userIds);
        cacheInvalidationEventPublisher.publishPermissionChanged(userIds);
    }
}
