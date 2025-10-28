package com.base.shared.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NotFoundException;
import com.base.shared.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.shared.permission.application.command.port.in.DeletePermissionUseCase;
import com.base.shared.permission.domain.model.Permission;
import com.base.shared.permission.domain.port.out.PermissionRepository;
import com.base.shared.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeletePermissionHandler implements DeletePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long permissionId) {
        Permission existing = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.disable();
        permissionRepository.save(existing);
        evictAuthorityCacheForPermission(existing.getPermissionId().permissionId());
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
