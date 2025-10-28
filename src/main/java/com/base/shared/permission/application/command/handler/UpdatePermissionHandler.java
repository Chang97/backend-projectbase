package com.base.shared.permission.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.shared.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.shared.permission.application.command.dto.PermissionCommand;
import com.base.shared.permission.application.command.dto.PermissionCommandResult;
import com.base.shared.permission.application.command.mapper.PermissionCommandMapper;
import com.base.shared.permission.application.command.port.in.UpdatePermissionUseCase;
import com.base.shared.permission.domain.model.Permission;
import com.base.shared.permission.domain.port.out.PermissionRepository;
import com.base.shared.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdatePermissionHandler implements UpdatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final PermissionCommandMapper permissionCommandMapper;

    @Override
    public PermissionCommandResult handle(Long permissionId, PermissionCommand command) {
        Permission existing = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));

        if (command.permissionCode() != null
                && !command.permissionCode().equals(existing.getPermissionCode())
                && permissionRepository.existsByPermissionCode(command.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + command.permissionCode());
        }

        Permission permission = permissionCommandMapper.toDomain(permissionId, command);
        
        Permission saved = permissionRepository.save(permission);
        evictAuthorityCacheForPermission(saved.getPermissionId().permissionId());
        return permissionCommandMapper.toResult(saved);
    }

    private void evictAuthorityCacheForPermission(Long permissionId) {
        List<Long> roleIds = rolePermissionMapRepository.findRoleIdsByPermissionId(permissionId);
        List<Long> userIds = roleIds.isEmpty() ? List.of() : userRoleMapRepository.findUserIdsByRoleIds(roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
