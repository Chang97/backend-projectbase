package com.base.contexts.authr.role.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.role.application.command.port.in.DeleteRoleUseCase;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.port.out.RoleCommandPort;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapCommandPort;
import com.base.platform.exception.NotFoundException;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteRoleHandler implements DeleteRoleUseCase {

    private final RoleCommandPort roleCommandPort;
    private final RolePermissionMapCommandPort rolePermissionMapCommandPort;
    private final UserRoleMapCommandPort userRoleMapCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long roleId) {
        Role existing = roleCommandPort.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existing.disable();
        roleCommandPort.save(existing);

        rolePermissionMapCommandPort.deleteAllByRoleId(roleId);
        List<Long> affectedUsers = userRoleMapCommandPort.findUserIdsByRoleIds(List.of(roleId));
        userRoleMapCommandPort.deleteAllByRoleId(roleId);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(affectedUsers);
        authorityCacheEventPort.publishPermissionsChanged(affectedUsers);
    }
}
