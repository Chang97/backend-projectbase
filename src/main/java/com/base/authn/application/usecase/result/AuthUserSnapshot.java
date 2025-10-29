package com.base.authn.application.usecase.result;

public record AuthUserSnapshot(
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
