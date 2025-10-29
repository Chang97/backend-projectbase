package com.base.authn.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.authn.adapter.in.web.dto.LoginResponse;
import com.base.authn.adapter.in.web.dto.LoginResult;
import com.base.authn.adapter.in.web.dto.UserSummaryResponse;
import com.base.authn.application.usecase.result.AuthExecutionResult;
import com.base.authn.application.usecase.result.AuthSession;
import com.base.authn.application.usecase.result.AuthUserSnapshot;

@Component
public class AuthResponseAssembler {

    public LoginResult toLoginResult(AuthExecutionResult executionResult) {
        LoginResponse response = toLoginResponse(executionResult.session());
        return new LoginResult(response, executionResult.cookies());
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
