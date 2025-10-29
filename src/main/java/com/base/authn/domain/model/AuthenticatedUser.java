package com.base.authn.domain.model;

import java.util.List;

public record AuthenticatedUser(
        Long userId,
        String loginId,
        String password,
        boolean enabled,
        List<String> authorities
) {

    public AuthenticatedUser {
        authorities = authorities == null ? List.of() : List.copyOf(authorities);
    }

    public List<String> authorities() {
        return authorities;
    }
}
