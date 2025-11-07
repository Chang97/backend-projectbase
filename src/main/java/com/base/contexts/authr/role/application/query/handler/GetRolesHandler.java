package com.base.contexts.authr.role.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.role.application.query.dto.RoleQuery;
import com.base.contexts.authr.role.application.query.dto.RoleQueryResult;
import com.base.contexts.authr.role.application.query.mapper.RoleQueryMapper;
import com.base.contexts.authr.role.application.query.port.in.GetRolesUseCase;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.model.RoleFilter;
import com.base.contexts.authr.role.domain.port.out.RoleQueryPort;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRolesHandler implements GetRolesUseCase {

    private final RoleQueryPort roleQueryPort;
    private final RolePermissionMapQueryPort rolePermissionMapQueryPort;
    private final RoleQueryMapper roleQueryMapper;

    @Override
    public List<RoleQueryResult> handle(RoleQuery query) {
        RoleFilter filter = roleQueryMapper.toFilter(query);
        List<Role> roles = roleQueryPort.search(filter);
        java.util.Map<Long, List<Long>> permissionsByRole = roles.isEmpty()
                ? java.util.Collections.emptyMap()
                : rolePermissionMapQueryPort.findByRoleIds(
                                roles.stream()
                                        .map(role -> role.getRoleId().value())
                                        .toList())
                        .stream()
                        .collect(java.util.stream.Collectors.groupingBy(
                                RolePermissionMap::getRoleId,
                                java.util.stream.Collectors.mapping(RolePermissionMap::getPermissionId, java.util.stream.Collectors.toList())));

        return roles.stream()
                .map(role -> roleQueryMapper.toResult(role,
                        permissionsByRole.getOrDefault(role.getRoleId().value(), List.of())))
                .toList();
    }
}
