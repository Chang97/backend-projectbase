package com.base.api.auth.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.api.auth.dto.LoginResponse;
import com.base.api.auth.dto.LoginResult;
import com.base.api.menu.assembler.MenuResponseAssembler;
import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.dto.MenuTreeResponse;
import com.base.api.user.assembler.UserResponseAssembler;
import com.base.api.user.dto.UserResponse;
import com.base.application.auth.usecase.result.AuthExecutionResult;
import com.base.application.auth.usecase.result.AuthSession;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthResponseAssembler {

    private final UserResponseAssembler userResponseAssembler;
    private final MenuResponseAssembler menuResponseAssembler;

    public LoginResult toLoginResult(AuthExecutionResult executionResult) {
        LoginResponse response = toLoginResponse(executionResult.session());
        return new LoginResult(response, executionResult.cookies());
    }

    public LoginResponse toLoginResponse(AuthSession session) {
        UserResponse user = userResponseAssembler.toResponse(session.user());
        List<MenuTreeResponse> menus = menuResponseAssembler.toTreeResponses(session.menuTree());
        List<MenuResponse> accessibleMenus = menuResponseAssembler.toResponses(session.accessibleMenus());
        return new LoginResponse(user, menus, accessibleMenus, session.permissions());
    }
}
