package com.base.contexts.authr.role.adapter.in.web.command.controller;

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

import com.base.contexts.authr.role.adapter.in.web.command.dto.RoleCommandRequest;
import com.base.contexts.authr.role.adapter.in.web.command.dto.RoleCommandResponse;
import com.base.contexts.authr.role.adapter.in.web.command.mapper.RoleCommandWebMapper;
import com.base.contexts.authr.role.application.command.dto.RoleCommand;
import com.base.contexts.authr.role.application.command.dto.RoleCommandResult;
import com.base.contexts.authr.role.application.command.port.in.CreateRoleUseCase;
import com.base.contexts.authr.role.application.command.port.in.DeleteRoleUseCase;
import com.base.contexts.authr.role.application.command.port.in.UpdateRoleUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authr/roles")
@RequiredArgsConstructor
public class RoleCommandController {

    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final RoleCommandWebMapper roleCommandWebMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleCommandResponse> createRole(@Valid @RequestBody RoleCommandRequest request) {
        RoleCommand command = roleCommandWebMapper.toCommand(request);
        RoleCommandResult result = createRoleUseCase.handle(command);
        URI location = URI.create("/api/authr/roles/" + result.roleId());
        return ResponseEntity.created(location)
                .body(roleCommandWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleCommandResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleCommandRequest request) {
        RoleCommand command = roleCommandWebMapper.toCommand(request);
        RoleCommandResult result = updateRoleUseCase.handle(id, command);
        return ResponseEntity.ok(roleCommandWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        deleteRoleUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}
