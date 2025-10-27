package com.base.identity.user.adapter.in.web.command;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.identity.user.adapter.in.web.dto.UserRequest;
import com.base.identity.user.adapter.in.web.dto.UserResponse;
import com.base.identity.user.adapter.in.web.mapper.UserCommandRequestMapper;
import com.base.identity.user.adapter.in.web.mapper.UserResponseMapper;
import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.application.port.in.command.CreateUserUseCase;
import com.base.identity.user.application.command.dto.UserResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCommandController {
    
    private final CreateUserUseCase createUserUseCase;
    // private final UpdateUserUseCase updateUserUseCase;
    // private final DeleteUserUseCase deleteUserUseCase;
    // private final ChangePasswordUseCase changePasswordUseCase;
    // private final UserCommandAssembler userCommandAssembler;
    // private final UserResponseAssembler userResponseAssembler;
    private final UserCommandRequestMapper userCommandMapper;
    private final UserResponseMapper userResponseMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        CreateUserCommand command = userCommandMapper.toCreateCommand(request);
        UserResult result = createUserUseCase.handle(command);
        return ResponseEntity.ok(userResponseMapper.toResponse(result));
    }

    // @PutMapping("/{id}")
    // @PreAuthorize("hasAuthority('USER_UPDATE')")
    // public ResponseEntity<UserResponse> updateUser(
    //         @PathVariable Long id, @RequestBody UserRequest request) {
    //     UpdateUserCommand command = userCommandAssembler.toUpdateCommand(request);
    //     UserResult result = updateUserUseCase.handle(id, command);
    //     return ResponseEntity.ok(userResponseAssembler.toResponse(result));
    // }

    // @PutMapping("/{id}/password")
    // @PreAuthorize("hasAuthority('USER_CHANGE_PASSWORD')")
    // public ResponseEntity<Void> changePassword(
    //         @PathVariable Long id, @RequestBody PasswordChangeRequest request) {
    //     ChangePasswordCommand command = userCommandAssembler.toChangePasswordCommand(id, request);
    //     changePasswordUseCase.handle(command);
    //     return ResponseEntity.noContent().build();
    // }

    // @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('USER_DELETE')")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    //     deleteUserUseCase.handle(id);
    //     return ResponseEntity.noContent().build();
    // }

}
