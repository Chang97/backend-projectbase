package com.base.identity.user.domain.model;

import java.util.Set;

public record UserFilter(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        Long statusCodeId,
        Boolean useYn,
        Set<Long> userIds
) {

    public static UserFilter empty() {
        return new UserFilter(null, null, null, null, null, null, null, Set.of());
    }
}
