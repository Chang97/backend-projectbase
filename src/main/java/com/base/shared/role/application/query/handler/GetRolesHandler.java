package com.base.shared.role.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.shared.role.application.query.dto.RoleQuery;
import com.base.shared.role.application.query.dto.RoleQueryResult;
import com.base.shared.role.application.query.mapper.RoleQueryMapper;
import com.base.shared.role.application.query.port.in.GetRolesUseCase;
import com.base.shared.role.domain.model.Role;
import com.base.shared.role.domain.model.RoleFilter;
import com.base.shared.role.domain.port.out.RoleRepository;
import com.base.shared.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.shared.rolepermissionmap.domain.port.out.RolePermissionMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRolesHandler implements GetRolesUseCase {

    private final RoleRepository roleRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final RoleQueryMapper roleQueryMapper;

    @Override
    public List<RoleQueryResult> handle(RoleQuery query) {
        RoleFilter filter = roleQueryMapper.toFilter(query);
        List<Role> roles = roleRepository.search(filter);
        java.util.Map<Long, List<Long>> permissionsByRole = roles.isEmpty()
                ? java.util.Collections.emptyMap()
                : rolePermissionMapRepository.findByRoleIds(
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
