package com.base.identity.user.adapter.in.web.query;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.user.assembler.UserResponseAssembler;
import com.base.api.user.dto.LoginIdCheckResponse;
import com.base.api.user.dto.UserResponse;
import com.base.application.user.port.in.CheckLoginIdUseCase;
import com.base.application.user.port.in.GetUserUseCase;
import com.base.application.user.port.in.GetUsersUseCase;
import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.application.user.usecase.result.LoginIdAvailabilityResult;
import com.base.application.user.usecase.result.UserResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserQueryController {
    
    private final GetUserUseCase getUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CheckLoginIdUseCase checkLoginIdUseCase;
    private final UserResponseAssembler userResponseAssembler;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResult result = getUserUseCase.handle(id);
        return ResponseEntity.ok(userResponseAssembler.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_LIST')")
    public ResponseEntity<List<UserResponse>> getUsers(
        @ModelAttribute UserSearchCondition condition
    ) {
        return ResponseEntity.ok(
                userResponseAssembler.toResponses(getUsersUseCase.handle(condition))
        );
    }

    @GetMapping("/check-login-id")
    @PreAuthorize("hasAuthority('USER_CHECK_LOGIN_ID')")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId(@RequestParam String loginId) {
        LoginIdAvailabilityResult result = checkLoginIdUseCase.handle(loginId);
        return ResponseEntity.ok(userResponseAssembler.toResponse(result));
    }

}
