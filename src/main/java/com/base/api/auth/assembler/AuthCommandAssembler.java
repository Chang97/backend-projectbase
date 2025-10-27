package com.base.api.auth.assembler;

import org.springframework.stereotype.Component;

import com.base.api.auth.dto.LoginRequest;
import com.base.application.auth.usecase.command.LoginCommand;
import com.base.application.auth.usecase.command.LogoutCommand;
import com.base.application.auth.usecase.command.RefreshTokenCommand;

@Component
public class AuthCommandAssembler {

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
