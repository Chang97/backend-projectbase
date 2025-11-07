package com.base.contexts.identity.user.application.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserQueryResult(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        String empNo,
        String positionName,
        String tel,
        Long statusCodeId,
        Boolean useYn,
        LocalDateTime passwordUpdatedAt,
        Integer passwordFailCount,
        List<Long> roleIds
) {
}
