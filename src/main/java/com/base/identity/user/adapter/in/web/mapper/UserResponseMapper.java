package com.base.identity.user.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.identity.user.adapter.in.web.dto.LoginIdCheckResponse;
import com.base.identity.user.adapter.in.web.dto.UserResponse;
import com.base.identity.user.application.command.dto.LoginIdAvailabilityResult;
import com.base.identity.user.application.command.dto.UserResult;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(UserResult result) {
        if (result == null) {
            return null;
        }
        return new UserResponse(
                result.userId(),
                result.email(),
                result.loginId(),
                result.userName(),
                result.orgId(),
                result.orgName(),
                result.empNo(),
                result.pstnName(),
                result.tel(),
                result.userStatusId(),
                result.userStatusName(),
                result.useYn(),
                result.roleIds(),
                result.roleNames(),
                result.roleNameList()
        );
    }

    public List<UserResponse> toResponses(List<UserResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

    public LoginIdCheckResponse toResponse(LoginIdAvailabilityResult result) {
        return new LoginIdCheckResponse(result.available());
    }
}
