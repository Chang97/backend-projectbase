package com.base.authn.domain.model;

import java.util.Collection;
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

    public Collection<String> authorities() {
        return authorities;
    }
}
