package com.base.contexts.organization.adapter.in.web.command.controller;

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

import com.base.contexts.organization.adapter.in.web.command.dto.OrgCommandRequest;
import com.base.contexts.organization.adapter.in.web.command.dto.OrgCommandResponse;
import com.base.contexts.organization.adapter.in.web.command.mapper.OrgCommandWebMapper;
import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;
import com.base.contexts.organization.application.command.port.in.CreateOrgUseCase;
import com.base.contexts.organization.application.command.port.in.DeleteOrgUseCase;
import com.base.contexts.organization.application.command.port.in.UpdateOrgUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orgs")
@RequiredArgsConstructor
public class OrgCommandController {

    private final CreateOrgUseCase createOrgUseCase;
    private final UpdateOrgUseCase updateOrgUseCase;
    private final DeleteOrgUseCase deleteOrgUseCase;
    private final OrgCommandWebMapper commandMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ORG_CREATE')")
    public ResponseEntity<OrgCommandResponse> create(@Valid @RequestBody OrgCommandRequest request) {
        OrgCommand command = commandMapper.toCommand(request);
        OrgCommandResult result = createOrgUseCase.handle(command);
        URI location = URI.create("/api/orgs/" + result.orgId());
        return ResponseEntity.created(location).body(commandMapper.toResponse(result));
    }

    @PutMapping("/{orgId}")
    @PreAuthorize("hasAuthority('ORG_UPDATE')")
    public ResponseEntity<OrgCommandResponse> update(@PathVariable Long orgId,
            @Valid @RequestBody OrgCommandRequest request) {
        OrgCommand command = commandMapper.toCommand(request);
        OrgCommandResult result = updateOrgUseCase.handle(orgId, command);
        return ResponseEntity.ok(commandMapper.toResponse(result));
    }

    @DeleteMapping("/{orgId}")
    @PreAuthorize("hasAuthority('ORG_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long orgId) {
        deleteOrgUseCase.handle(orgId);
        return ResponseEntity.noContent().build();
    }
}
