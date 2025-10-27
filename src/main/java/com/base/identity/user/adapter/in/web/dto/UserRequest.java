package com.base.identity.user.adapter.in.web.dto;

import java.util.List;

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
        Boolean useYn,
        List<Long> roleIds
) {}
