package com.base.identity.user.adapter.in.web.query.dto;

public record UserQueryRequest(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        Long statusCodeId,
        Boolean useYn
) {
}
