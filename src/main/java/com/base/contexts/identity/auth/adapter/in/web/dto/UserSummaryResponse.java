package com.base.contexts.identity.auth.adapter.in.web.dto;

public record UserSummaryResponse(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        String empNo,
        String positionName,
        String tel,
        Boolean useYn
) {
}
