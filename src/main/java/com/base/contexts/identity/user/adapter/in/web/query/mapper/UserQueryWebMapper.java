package com.base.contexts.identity.user.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.user.adapter.in.web.query.dto.LoginIdCheckResponse;
import com.base.contexts.identity.user.adapter.in.web.query.dto.UserQueryRequest;
import com.base.contexts.identity.user.adapter.in.web.query.dto.UserQueryResponse;
import com.base.contexts.identity.user.application.query.dto.LoginIdAvailabilityResult;
import com.base.contexts.identity.user.application.query.dto.UserQuery;
import com.base.contexts.identity.user.application.query.dto.UserQueryResult;

@Component
public class UserQueryWebMapper {

    public UserQuery toQuery(UserQueryRequest request) {
        if (request == null) {
            return new UserQuery(null, null, null, null, null, null, null);
        }
        return new UserQuery(
                request.userId(),
                request.email(),
                request.loginId(),
                request.userName(),
                request.orgId(),
                request.statusCodeId(),
                request.useYn()
        );
    }

    public UserQueryResponse toResponse(UserQueryResult result) {
        return new UserQueryResponse(
                result.userId(),
                result.email(),
                result.loginId(),
                result.userName(),
                result.orgId(),
                result.empNo(),
                result.positionName(),
                result.tel(),
                result.statusCodeId(),
                result.useYn(),
                result.passwordUpdatedAt(),
                result.passwordFailCount(),
                result.roleIds()
        );
    }

    public List<UserQueryResponse> toResponses(List<UserQueryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

    public LoginIdCheckResponse toResponse(LoginIdAvailabilityResult result) {
        return new LoginIdCheckResponse(result.loginId(), result.available());
    }
}
