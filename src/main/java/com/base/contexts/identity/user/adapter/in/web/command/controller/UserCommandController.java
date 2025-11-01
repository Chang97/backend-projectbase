package com.base.contexts.identity.user.adapter.in.web.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.identity.user.adapter.in.web.command.dto.UserCommandRequest;
import com.base.contexts.identity.user.adapter.in.web.command.dto.UserCommandResponse;
import com.base.contexts.identity.user.adapter.in.web.command.dto.UserPasswordRequest;
import com.base.contexts.identity.user.adapter.in.web.command.mapper.UserCommandWebMapper;
import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.application.command.port.in.ChangePasswordUseCase;
import com.base.contexts.identity.user.application.command.port.in.CreateUserUseCase;
import com.base.contexts.identity.user.application.command.port.in.DeleteUserUseCase;
import com.base.contexts.identity.user.application.command.port.in.UpdateUserUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/identity/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final UserCommandWebMapper userCommandWebMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserCommandResponse> createUser(@RequestBody UserCommandRequest request) {
        UserCommand command = userCommandWebMapper.toCommand(request);
        return ResponseEntity.ok(
                userCommandWebMapper.toResponse(createUserUseCase.handle(command))
        );
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserCommandResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserCommandRequest request) {
        UserCommand command = userCommandWebMapper.toCommand(request);
        return ResponseEntity.ok(
                userCommandWebMapper.toResponse(updateUserUseCase.handle(userId, command))
        );
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("hasAuthority('USER_CHANGE_PASSWORD')")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long userId,
            @RequestBody UserPasswordRequest request) {
        changePasswordUseCase.handle(userCommandWebMapper.toPasswordCommand(userId, request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        deleteUserUseCase.handle(userId);
        return ResponseEntity.noContent().build();
    }
}
