package com.base.contexts.authr.role.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.role.application.query.dto.RoleQueryResult;
import com.base.contexts.authr.role.application.query.mapper.RoleQueryMapper;
import com.base.contexts.authr.role.application.query.port.in.GetRoleUseCase;
import com.base.contexts.authr.role.domain.port.out.RoleRepository;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRoleHandler implements GetRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleQueryMapper roleQueryMapper;
    private final RolePermissionMapRepository rolePermissionMapRepository;

    @Override
    public RoleQueryResult handle(Long roleId) {
        return roleRepository.findById(roleId)
                .map(role -> roleQueryMapper.toResult(role,
                        rolePermissionMapRepository.findByRoleId(roleId).stream()
                                .map(RolePermissionMap::getPermissionId)
                                .toList()))
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
