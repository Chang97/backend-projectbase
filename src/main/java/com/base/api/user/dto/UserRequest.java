package com.base.api.user.dto;

public record UserRequest(
        String email,
        String loginId,
        String userPassword,
        String userName,
        Long orgId,
        String empNo,
        String pstnName,
        String tel,
        Long userStatusId,
        Boolean useYn
) {}
