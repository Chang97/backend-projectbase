package com.base.shared.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.ConflictException;
import com.base.shared.permission.application.command.dto.PermissionCommand;
import com.base.shared.permission.application.command.dto.PermissionCommandResult;
import com.base.shared.permission.application.command.mapper.PermissionCommandMapper;
import com.base.shared.permission.application.command.port.in.CreatePermissionUseCase;
import com.base.shared.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.shared.permission.domain.model.Permission;
import com.base.shared.permission.domain.model.PermissionId;
import com.base.shared.permission.domain.port.out.PermissionRepository;
import com.base.shared.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreatePermissionHandler implements CreatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final PermissionCommandMapper permissionCommandMapper;

    @Override
    public PermissionCommandResult handle(PermissionCommand command) {
        if (permissionRepository.existsByPermissionCode(command.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + command.permissionCode());
        }

        Permission permission = permissionCommandMapper.toDomain(command);

        Permission saved = permissionRepository.save(permission);
        PermissionId savedId = saved.getPermissionId();
        if (savedId != null) {
            evictAuthorityCacheForPermission(savedId.permissionId());
        }
        return permissionCommandMapper.toResult(saved);
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
