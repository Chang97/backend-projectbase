package com.base.identity.user.application.command.dto;

import java.util.List;

public record CreateUserCommand(
        String email,
        String loginId,
        String rawPassword,
        String userName,
        Long orgId,
        String empNo,
        String pstnName,
        String tel,
        Long userStatusCodeId,
        Boolean useYn,
        List<Long> roleIds
) {
}
