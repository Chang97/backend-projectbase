package com.base.contexts.identity.authn.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.authn.adapter.in.web.dto.LoginRequest;
import com.base.contexts.identity.authn.application.usecase.dto.LoginCommand;
import com.base.contexts.identity.authn.application.usecase.dto.LogoutCommand;
import com.base.contexts.identity.authn.application.usecase.dto.RefreshTokenCommand;

@Component
public class AuthCommandRequestMapper {

    public LoginCommand toLoginCommand(LoginRequest request) {
        return new LoginCommand(request.loginId(), request.password());
    }

    public RefreshTokenCommand toRefreshCommand(String refreshToken) {
        return new RefreshTokenCommand(refreshToken);
    }

    public LogoutCommand toLogoutCommand(String refreshToken) {
        return new LogoutCommand(refreshToken);
    }
}
