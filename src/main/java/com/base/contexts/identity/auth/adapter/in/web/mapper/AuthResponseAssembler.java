package com.base.contexts.identity.auth.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.auth.adapter.in.web.dto.LoginResponse;
import com.base.contexts.identity.auth.adapter.in.web.dto.UserSummaryResponse;
import com.base.contexts.identity.auth.application.dto.AuthExecutionResult;
import com.base.contexts.identity.auth.application.dto.AuthSession;
import com.base.contexts.identity.auth.application.dto.AuthUserSnapshot;

@Component
public class AuthResponseAssembler {

    public LoginResponse toLoginResponse(AuthExecutionResult executionResult) {
        return toLoginResponse(executionResult.session());
    }

    public LoginResponse toLoginResponse(AuthSession session) {
        return new LoginResponse(
                toUserResponse(session.user()),
                session.permissions()
        );
    }

    private UserSummaryResponse toUserResponse(AuthUserSnapshot snapshot) {
        if (snapshot == null) {
            return null;
        }
        return new UserSummaryResponse(
                snapshot.userId(),
                snapshot.email(),
                snapshot.loginId(),
                snapshot.userName(),
                snapshot.orgId(),
                snapshot.empNo(),
                snapshot.positionName(),
                snapshot.tel(),
                snapshot.useYn()
        );
    }

}
