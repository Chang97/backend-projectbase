package com.base.contexts.authr.userrolemap.adapter.out.persistence.service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.port.out.RoleRepository;
import com.base.contexts.authr.userrolemap.domain.model.UserRoleMap;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapRepository;
import com.base.contexts.identity.user.domain.port.out.UserRoleAssignmentPort;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRoleAssignmentAdapter implements UserRoleAssignmentPort{

    private final RoleRepository roleRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void replaceUserRoles(Long userId, List<Long> roleIds) {
        syncRoles(userId, roleIds);
        authorityCacheEventPort.publishRoleAuthoritiesChanged(List.of(userId));
    }

    private void syncRoles(Long userId, List<Long> roleIds) {
        List<Long> sanitized = sanitizeRoleIds(roleIds);
        userRoleMapRepository.deleteAllByUserId(userId);
        if (sanitized.isEmpty()) {
            return;
        }
        validateRoleExistence(sanitized);
        List<UserRoleMap> mappings = sanitized.stream()
                .map(roleId -> UserRoleMap.of(userId, roleId))
                .toList();
        userRoleMapRepository.saveAll(mappings);
    }

    private List<Long> sanitizeRoleIds(List<Long> roleIds) {
        if (roleIds == null) {
            return Collections.emptyList();
        }
        Set<Long> distinct = roleIds.stream()
                .filter(Objects::nonNull)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
        return List.copyOf(distinct);
    }

    private void validateRoleExistence(List<Long> roleIds) {
        List<Role> roles = roleRepository.findAllByIds(roleIds);
        Set<Long> existing = roles.stream()
                .map(role -> role.getRoleId() != null ? role.getRoleId().value() : null)
                .filter(Objects::nonNull)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
        if (existing.size() != roleIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }
    }
}
