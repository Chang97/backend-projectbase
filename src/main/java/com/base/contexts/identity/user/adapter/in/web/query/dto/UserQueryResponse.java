package com.base.contexts.identity.user.adapter.in.web.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserQueryResponse(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        String empNo,
        String pstnName,
        String tel,
        Long statusCodeId,
        Boolean useYn,
        LocalDateTime passwordUpdatedAt,
        Integer passwordFailCount,
        List<Long> roleIds
) {
}
