package com.base.contexts.identity.auth.application;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCachePort;
import com.base.contexts.authr.permission.domain.port.out.PermissionRepository;
import com.base.contexts.authr.role.domain.port.out.RoleRepository;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapRepository;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapRepository;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthorityService {

    private final UserRoleMapRepository userRoleMapRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final PermissionRepository permissionRepository;
    private final AuthorityCachePort authorityCachePort;

    public Set<GrantedAuthority> loadAuthorities(Long userId) {
        if (userId == null) {
            return Set.of();
        }

        return authorityCachePort.get(userId)
                .map(this::toGrantedAuthorities)
                .orElseGet(() -> populateAndCache(userId));
    }

    public Set<GrantedAuthority> loadAuthoritiesOrEmpty(Long userId) {
        return userId != null ? loadAuthorities(userId) : Set.of();
    }

    private Set<GrantedAuthority> populateAndCache(Long userId) {
        LinkedHashSet<String> codes = new LinkedHashSet<>();

        List<Long> roleIds = userRoleMapRepository.findRoleIdsByUserId(userId);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleRepository.findAllByIds(roleIds).stream()
                    .map(role -> normalizeRoleName(role.getRoleName()))
                    .filter(StringUtils::hasText)
                    .forEach(codes::add);

            Set<Long> permissionIds = rolePermissionMapRepository.findByRoleIds(roleIds).stream()
                    .map(map -> map.getPermissionId())
                    .collect(Collectors.toSet());

            if (!permissionIds.isEmpty()) {
                permissionRepository.findAllByIds(permissionIds).stream()
                        .map(permission -> StringNormalizer.trimToNull(permission.getPermissionCode()))
                        .filter(StringUtils::hasText)
                        .forEach(codes::add);
            }
        }

        List<String> serialized = List.copyOf(codes);
        authorityCachePort.put(userId, serialized);
        return toGrantedAuthorities(serialized);
    }

    private Set<GrantedAuthority> toGrantedAuthorities(List<String> codes) {
        return codes.stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .filter(code -> !code.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String normalizeRoleName(String roleName) {
        if (!StringUtils.hasText(roleName)) {
            return "";
        }
        String trimmed = roleName.trim();
        return trimmed.startsWith("ROLE_") ? trimmed : "ROLE_" + trimmed;
    }
}
