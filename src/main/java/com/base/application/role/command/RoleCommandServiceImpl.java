package com.base.application.role.command;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.api.role.mapper.RoleMapper;
import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.role.query.RoleResponseAssembler;
import com.base.domain.mapping.RolePermissionMap;
import com.base.domain.mapping.RolePermissionMapRepository;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final PermissionRepository permissionRepository;
    private final RoleResponseAssembler roleResponseAssembler;
    private final UserRoleMapRepository userRoleMapRepository;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByRoleName(request.roleName())) {
            throw new ConflictException("Role name already exists: " + request.roleName());
        }
        Role role = roleMapper.toEntity(request);
        if (role.getUseYn() == null) {
            role.setUseYn(Boolean.TRUE);
        }
        Role saved = roleRepository.save(role);
        syncRolePermissions(saved, request.permissionIds());
        evictAuthorityCacheForRole(saved.getRoleId());
        List<Long> permissionIds = rolePermissionMapRepository.findPermissionIdsByRoleId(saved.getRoleId());
        return roleResponseAssembler.assemble(saved, permissionIds);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        if (!existing.getRoleName().equals(request.roleName())
                && roleRepository.existsByRoleName(request.roleName())) {
            throw new ConflictException("Role name already exists: " + request.roleName());
        }

        existing.setRoleName(request.roleName());
        if (request.useYn() != null) {
            existing.setUseYn(request.useYn());
        }
        Role saved = roleRepository.save(existing);
        syncRolePermissions(saved, request.permissionIds());
        evictAuthorityCacheForRole(saved.getRoleId());
        List<Long> permissionIds = rolePermissionMapRepository.findPermissionIdsByRoleId(saved.getRoleId());
        return roleResponseAssembler.assemble(saved, permissionIds);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        existing.setUseYn(false);
        roleRepository.save(existing);
        rolePermissionMapRepository.deleteByRoleRoleId(id);
        evictAuthorityCacheForRole(id);
    }

    private void syncRolePermissions(Role role, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }
        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        rolePermissionMapRepository.deleteByRoleRoleId(role.getRoleId());

        if (sanitizedIds.isEmpty()) {
            return;
        }

        Map<Long, Permission> permissionMap = permissionRepository.findAllById(sanitizedIds).stream()
                .collect(Collectors.toMap(Permission::getPermissionId, Function.identity()));

        if (permissionMap.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        rolePermissionMapRepository.saveAll(
                sanitizedIds.stream()
                        .map(id -> RolePermissionMap.builder()
                                .role(role)
                                .permission(permissionMap.get(id))
                                .build())
                        .toList()
        );
    }

    private void evictAuthorityCacheForRole(Long roleId) {
        List<Long> userIds = userRoleMapRepository.findUserIdsByRoleIds(List.of(roleId));
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(userIds);
    }
}
