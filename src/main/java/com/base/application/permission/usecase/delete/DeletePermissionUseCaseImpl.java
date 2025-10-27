package com.base.application.permission.usecase.delete;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeletePermissionUseCaseImpl implements DeletePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;

    @Override
    public void handle(Long permissionId) {
        Permission existing = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.setUseYn(false);
        permissionRepository.save(existing);
        evictAuthorityCacheForPermission(existing.getPermissionId());
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(userIds);
        cacheInvalidationEventPublisher.publishPermissionChanged(userIds);
    }
}
