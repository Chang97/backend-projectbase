package com.base.infra.persistence.role;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.base.application.role.port.out.RolePermissionPort;
import com.base.domain.mapping.RolePermissionMap;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.role.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaRolePermissionAdapter implements RolePermissionPort {

    private final RolePermissionMapRepository rolePermissionMapRepository;

    @Override
    public void clearPermissions(Long roleId) {
        rolePermissionMapRepository.deleteByRoleRoleId(roleId);
    }

    @Override
    public void assignPermissions(Role role, List<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        List<RolePermissionMap> mappings = permissions.stream()
                .map(permission -> RolePermissionMap.builder()
                        .role(role)
                        .permission(permission)
                        .build())
                .toList();
        rolePermissionMapRepository.saveAll(mappings);
    }

    @Override
    public List<Long> findPermissionIdsByRoleId(Long roleId) {
        return rolePermissionMapRepository.findPermissionIdsByRoleId(roleId);
    }

    @Override
    public Map<Long, List<Long>> findPermissionIdsByRoleIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Map.of();
        }
        return rolePermissionMapRepository.findMappingsByRoleIds(roleIds).stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        LinkedHashMap::new,
                        Collectors.mapping(row -> (Long) row[1], Collectors.toList())
                ));
    }
}
