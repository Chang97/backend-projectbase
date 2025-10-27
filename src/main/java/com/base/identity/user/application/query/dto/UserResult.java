package com.base.identity.user.application.query.dto;

import java.util.List;

public record UserResult(
        Long userId,
        String email,
        String loginId,
        String userName,
        Long orgId,
        String orgName,
        String empNo,
        String pstnName,
        String tel,
        Long userStatusId,
        String userStatusName,
        Boolean useYn,
        List<Long> roleIds,
        List<String> roleNames,
        String roleNameList
) {
}
