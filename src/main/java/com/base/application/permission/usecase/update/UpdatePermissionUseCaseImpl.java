package com.base.application.permission.usecase.update;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.permission.usecase.command.UpdatePermissionCommand;
import com.base.application.permission.usecase.query.assembler.PermissionResultAssembler;
import com.base.application.permission.usecase.result.PermissionResult;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdatePermissionUseCaseImpl implements UpdatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;
    private final PermissionResultAssembler permissionResultAssembler;

    @Override
    public PermissionResult handle(Long permissionId, UpdatePermissionCommand command) {
        Permission existing = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));

        if (command.permissionCode() != null
                && !command.permissionCode().equals(existing.getPermissionCode())
                && permissionRepository.existsByPermissionCode(command.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + command.permissionCode());
        }

        applyUpdates(existing, command);
        Permission saved = permissionRepository.save(existing);
        evictAuthorityCacheForPermission(saved.getPermissionId());
        return permissionResultAssembler.toResult(saved);
    }

    private void applyUpdates(Permission permission, UpdatePermissionCommand command) {
        if (command.permissionCode() != null) {
            permission.setPermissionCode(command.permissionCode());
        }
        if (command.permissionName() != null) {
            permission.setPermissionName(command.permissionName());
        }
        if (command.useYn() != null) {
            permission.setUseYn(command.useYn());
        }
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(userIds);
        cacheInvalidationEventPublisher.publishPermissionChanged(userIds);
    }
}
