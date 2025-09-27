package com.base.api.user.dto;

public record UserResponse(
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
        Boolean useYn
) {}
