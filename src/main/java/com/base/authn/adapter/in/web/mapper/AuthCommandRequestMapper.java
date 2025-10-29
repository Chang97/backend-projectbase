package com.base.authn.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.base.authn.adapter.in.web.dto.LoginRequest;
import com.base.authn.application.usecase.command.LoginCommand;
import com.base.authn.application.usecase.command.LogoutCommand;
import com.base.authn.application.usecase.command.RefreshTokenCommand;

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
