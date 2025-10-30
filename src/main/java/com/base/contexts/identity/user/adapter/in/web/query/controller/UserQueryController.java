package com.base.contexts.identity.user.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.identity.user.adapter.in.web.query.dto.LoginIdCheckResponse;
import com.base.contexts.identity.user.adapter.in.web.query.dto.UserQueryRequest;
import com.base.contexts.identity.user.adapter.in.web.query.dto.UserQueryResponse;
import com.base.contexts.identity.user.adapter.in.web.query.mapper.UserQueryWebMapper;
import com.base.contexts.identity.user.application.query.port.in.CheckLoginIdUseCase;
import com.base.contexts.identity.user.application.query.port.in.GetUserUseCase;
import com.base.contexts.identity.user.application.query.port.in.GetUsersUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserQueryController {

    private final GetUserUseCase getUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CheckLoginIdUseCase checkLoginIdUseCase;
    private final UserQueryWebMapper userQueryWebMapper;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserQueryResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                userQueryWebMapper.toResponse(getUserUseCase.handle(userId))
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_LIST')")
    public ResponseEntity<List<UserQueryResponse>> getUsers(@ModelAttribute UserQueryRequest request) {
        return ResponseEntity.ok(
                userQueryWebMapper.toResponses(getUsersUseCase.handle(userQueryWebMapper.toQuery(request)))
        );
    }

    @GetMapping("/check-login-id")
    @PreAuthorize("hasAuthority('USER_CHECK_LOGIN_ID')")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId(@RequestParam String loginId) {
        return ResponseEntity.ok(
                userQueryWebMapper.toResponse(checkLoginIdUseCase.handle(loginId))
        );
    }
}
