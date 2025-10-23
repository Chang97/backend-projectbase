package com.base.application.auth;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.auth.cache.AuthorityCacheService;
import com.base.domain.mapping.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthorityService {

    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheService authorityCacheService;

    public Set<GrantedAuthority> loadAuthorities(Long userId) {
        if (userId == null) {
            return Set.of();
        }

        return authorityCacheService.get(userId)
                .map(this::toGrantedAuthorities)
                .orElseGet(() -> populateAndCache(userId));
    }

    public Set<GrantedAuthority> loadAuthoritiesOrEmpty(Long userId) {
        return userId != null ? loadAuthorities(userId) : Set.of();
    }

    private Set<GrantedAuthority> populateAndCache(Long userId) {
        LinkedHashSet<String> codes = new LinkedHashSet<>();

        userRoleMapRepository.findRoleNamesByUserId(userId).stream()
                .filter(StringUtils::hasText)
                .map(this::normalizeRoleName)
                .forEach(codes::add);

        userRoleMapRepository.findPermissionCodesByUserId(userId).stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .filter(code -> !code.isEmpty())
                .forEach(codes::add);

        List<String> serialized = new ArrayList<>(codes);
        authorityCacheService.put(userId, serialized);

        return serialized.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
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
        String trimmed = roleName.trim();
        return trimmed.startsWith("ROLE_") ? trimmed : "ROLE_" + trimmed;
    }
}
