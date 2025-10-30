package com.base.contexts.authr.permission.adapter.in.web.command.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.authr.permission.adapter.in.web.command.dto.PermissionCommandRequest;
import com.base.contexts.authr.permission.adapter.in.web.command.dto.PermissionCommandResponse;
import com.base.contexts.authr.permission.adapter.in.web.command.mapper.PermissionCommandWebMapper;
import com.base.contexts.authr.permission.application.command.dto.PermissionCommandResult;
import com.base.contexts.authr.permission.application.command.port.in.CreatePermissionUseCase;
import com.base.contexts.authr.permission.application.command.port.in.DeletePermissionUseCase;
import com.base.contexts.authr.permission.application.command.port.in.UpdatePermissionUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionCommandController {
    
    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;
    private final PermissionCommandWebMapper permissionCommandWebMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<PermissionCommandResponse> createPermission(@RequestBody PermissionCommandRequest request) {
        PermissionCommandResult result = createPermissionUseCase.handle(permissionCommandWebMapper.toCommand(request));
        URI location = URI.create("/api/permission/" + result.permissionId());
        return ResponseEntity.created(location)
                .body(permissionCommandWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ResponseEntity<PermissionCommandResponse> updatePermission(
            @PathVariable Long id, @RequestBody PermissionCommandRequest request) {
        PermissionCommandResult result = updatePermissionUseCase.handle(id, permissionCommandWebMapper.toCommand(request));
        return ResponseEntity.ok(permissionCommandWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        deletePermissionUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

}
