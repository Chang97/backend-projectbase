package com.base.identity.user.application.command.dto;

import java.util.List;

public record UserCommand(
        String email,
        String loginId,
        String rawPassword,
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
