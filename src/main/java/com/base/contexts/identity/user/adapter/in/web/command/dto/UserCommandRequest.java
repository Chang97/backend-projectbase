package com.base.contexts.identity.user.adapter.in.web.command.dto;

import java.util.List;

public record UserCommandRequest(
        String email,
        String loginId,
        String password,
        String userName,
        Long orgId,
        String empNo,
        String positionName,
        String tel,
        Long statusCodeId,
        Boolean useYn,
        List<Long> roleIds
) {
}
