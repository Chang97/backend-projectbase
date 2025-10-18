package com.base.api.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.user.dto.PasswordChangeRequest;
import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.dto.LoginIdCheckResponse;
import com.base.application.user.command.UserCommandService;
import com.base.application.user.query.UserQueryService;
import com.base.application.user.query.UserSearchCondition;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userCommandService.createUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userCommandService.updateUser(id, request));
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('USER_CHANGE_PASSWORD')")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        userCommandService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCommandService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userQueryService.getUser(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_LIST')")
    public ResponseEntity<List<UserResponse>> getUsers(
        @ModelAttribute UserSearchCondition condition
    ) {
        return ResponseEntity.ok(userQueryService.getUsers(condition));
    }

    @GetMapping("/check-login-id")
    @PreAuthorize("hasAuthority('USER_CHECK_LOGIN_ID')")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId(@RequestParam String loginId) {
        boolean available = userQueryService.isLoginIdAvailable(loginId);
        return ResponseEntity.ok(new LoginIdCheckResponse(available));
    }

}
