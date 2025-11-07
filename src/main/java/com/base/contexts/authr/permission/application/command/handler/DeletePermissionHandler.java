package com.base.contexts.authr.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.permission.application.command.port.in.DeletePermissionUseCase;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.port.out.PermissionCommandPort;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeletePermissionHandler implements DeletePermissionUseCase {

    private final PermissionCommandPort permissionCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long permissionId) {
        Permission existing = permissionCommandPort.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.disable();
        permissionCommandPort.save(existing);
        evictAuthorityCacheForPermission(existing.getPermissionId().permissionId());
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapCommandPort.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapCommandPort.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
