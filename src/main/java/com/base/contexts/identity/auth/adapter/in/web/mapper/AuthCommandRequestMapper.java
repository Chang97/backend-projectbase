package com.base.contexts.identity.auth.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.auth.adapter.in.web.dto.LoginRequest;
import com.base.contexts.identity.auth.application.dto.LoginCommand;
import com.base.contexts.identity.auth.application.dto.LogoutCommand;
import com.base.contexts.identity.auth.application.dto.RefreshTokenCommand;

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
