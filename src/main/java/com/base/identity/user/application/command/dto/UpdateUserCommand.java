package com.base.identity.user.application.command.dto;

import java.util.List;

public record UpdateUserCommand(
        String email,
        String loginId,
        String rawPassword,
        String userName,
        Long orgId,
        String empNo,
        String pstnName,
        String tel,
        Long userStatusId,
        Boolean useYn,
        List<Long> roleIds
) {
}
