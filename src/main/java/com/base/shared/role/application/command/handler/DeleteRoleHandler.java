package com.base.shared.role.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NotFoundException;
import java.util.List;

import com.base.shared.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.shared.role.application.command.port.in.DeleteRoleUseCase;
import com.base.shared.role.domain.model.Role;
import com.base.shared.role.domain.port.out.RoleRepository;
import com.base.shared.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteRoleHandler implements DeleteRoleUseCase {

    private final RoleRepository roleRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long roleId) {
        Role existing = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        existing.disable();
        roleRepository.save(existing);

        rolePermissionMapRepository.deleteAllByRoleId(roleId);
        List<Long> affectedUsers = userRoleMapRepository.findUserIdsByRoleIds(List.of(roleId));
        userRoleMapRepository.deleteAllByRoleId(roleId);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(affectedUsers);
        authorityCacheEventPort.publishPermissionsChanged(affectedUsers);
    }
}
