package com.base.api.user.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.user.dto.LoginIdCheckResponse;
import com.base.api.user.dto.UserResponse;
import com.base.application.user.usecase.result.LoginIdAvailabilityResult;
import com.base.application.user.usecase.result.UserResult;

@Component
public class UserResponseAssembler {

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
