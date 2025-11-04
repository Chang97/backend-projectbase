package com.base.contexts.attachment.adapter.in.web.command.controller;

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

import com.base.contexts.attachment.adapter.in.web.command.dto.AtchFileCommandRequest;
import com.base.contexts.attachment.adapter.in.web.command.dto.AtchFileCommandResponse;
import com.base.contexts.attachment.adapter.in.web.command.mapper.AtchFileCommandWebMapper;
import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;
import com.base.contexts.attachment.application.command.port.in.CreateAtchFileUseCase;
import com.base.contexts.attachment.application.command.port.in.DeleteAtchFileUseCase;
import com.base.contexts.attachment.application.command.port.in.UpdateAtchFileUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AtchFileCommandController {

    private final CreateAtchFileUseCase createAtchFileUseCase;
    private final UpdateAtchFileUseCase updateAtchFileUseCase;
    private final DeleteAtchFileUseCase deleteAtchFileUseCase;
    private final AtchFileCommandWebMapper commandMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ATCH_FILE_CREATE')")
    public ResponseEntity<AtchFileCommandResponse> create(@Valid @RequestBody AtchFileCommandRequest request) {
        AtchFileCommand command = commandMapper.toCommand(request);
        AtchFileCommandResult result = createAtchFileUseCase.handle(command);
        URI location = URI.create("/api/attachments/" + result.atchFileId());
        return ResponseEntity.created(location).body(commandMapper.toResponse(result));
    }

    @PutMapping("/{atchFileId}")
    @PreAuthorize("hasAuthority('ATCH_FILE_UPDATE')")
    public ResponseEntity<AtchFileCommandResponse> update(@PathVariable Long atchFileId,
            @Valid @RequestBody AtchFileCommandRequest request) {
        AtchFileCommand command = commandMapper.toCommand(request);
        AtchFileCommandResult result = updateAtchFileUseCase.handle(atchFileId, command);
        return ResponseEntity.ok(commandMapper.toResponse(result));
    }

    @DeleteMapping("/{atchFileId}")
    @PreAuthorize("hasAuthority('ATCH_FILE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long atchFileId) {
        deleteAtchFileUseCase.handle(atchFileId);
        return ResponseEntity.noContent().build();
    }
}
