package com.base.application.auth;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.domain.mapping.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthorityService {

    private final UserRoleMapRepository userRoleMapRepository;

    public Set<GrantedAuthority> loadAuthorities(Long userId) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        List<String> roleNames = userRoleMapRepository.findRoleNamesByUserId(userId);
        roleNames.stream()
                .filter(StringUtils::hasText)
                .map(this::normalizeRoleName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);

        userRoleMapRepository.findPermissionCodesByUserId(userId).stream()
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);

        return authorities;
    }

    private String normalizeRoleName(String roleName) {
        String trimmed = roleName.trim();
        return trimmed.startsWith("ROLE_") ? trimmed : "ROLE_" + trimmed;
    }

    public Set<GrantedAuthority> loadAuthoritiesOrEmpty(Long userId) {
        return userId != null ? loadAuthorities(userId) : Set.of();
    }
}
