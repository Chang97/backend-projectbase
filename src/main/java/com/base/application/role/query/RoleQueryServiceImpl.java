package com.base.application.role.query;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.role.dto.RoleResponse;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final RoleResponseAssembler roleResponseAssembler;

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        List<Long> permissionIds = rolePermissionMapRepository.findPermissionIdsByRoleId(id);
        return roleResponseAssembler.assemble(role, permissionIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getRoles(RoleSearchCondition condition) {
        if (condition != null) {
            condition.normalize();
        }
        List<Role> roles = roleRepository.findAll(RoleSpecifications.withCondition(condition));
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, List<Long>> permissionMap = rolePermissionMapRepository
                .findMappingsByRoleIds(roles.stream().map(Role::getRoleId).toList()).stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(row -> (Long) row[1], Collectors.toList())));

        return roles.stream()
                .map(role -> roleResponseAssembler.assemble(
                        role,
                        permissionMap.getOrDefault(role.getRoleId(), List.of())))
                .toList();
    }
}
