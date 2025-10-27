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

import com.base.api.user.assembler.UserCommandAssembler;
import com.base.api.user.assembler.UserResponseAssembler;
import com.base.api.user.dto.LoginIdCheckResponse;
import com.base.api.user.dto.PasswordChangeRequest;
import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.application.user.port.in.ChangePasswordUseCase;
import com.base.application.user.port.in.CheckLoginIdUseCase;
import com.base.application.user.port.in.CreateUserUseCase;
import com.base.application.user.port.in.DeleteUserUseCase;
import com.base.application.user.port.in.GetUserUseCase;
import com.base.application.user.port.in.GetUsersUseCase;
import com.base.application.user.port.in.UpdateUserUseCase;
import com.base.application.user.usecase.command.ChangePasswordCommand;
import com.base.application.user.usecase.command.CreateUserCommand;
import com.base.application.user.usecase.command.UpdateUserCommand;
import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.application.user.usecase.result.LoginIdAvailabilityResult;
import com.base.application.user.usecase.result.UserResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CheckLoginIdUseCase checkLoginIdUseCase;
    private final UserCommandAssembler userCommandAssembler;
    private final UserResponseAssembler userResponseAssembler;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        CreateUserCommand command = userCommandAssembler.toCreateCommand(request);
        UserResult result = createUserUseCase.handle(command);
        return ResponseEntity.ok(userResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @RequestBody UserRequest request) {
        UpdateUserCommand command = userCommandAssembler.toUpdateCommand(request);
        UserResult result = updateUserUseCase.handle(id, command);
        return ResponseEntity.ok(userResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('USER_CHANGE_PASSWORD')")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        ChangePasswordCommand command = userCommandAssembler.toChangePasswordCommand(id, request);
        changePasswordUseCase.handle(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

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
