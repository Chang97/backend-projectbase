package com.base.contexts.authr.menu.adapter.in.web.command.controller;

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

import com.base.contexts.authr.menu.adapter.in.web.command.dto.MenuCommandRequest;
import com.base.contexts.authr.menu.adapter.in.web.command.dto.MenuCommandResponse;
import com.base.contexts.authr.menu.adapter.in.web.command.mapper.MenuCommandWebMapper;
import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.base.contexts.authr.menu.application.command.port.in.CreateMenuUseCase;
import com.base.contexts.authr.menu.application.command.port.in.DeleteMenuUseCase;
import com.base.contexts.authr.menu.application.command.port.in.UpdateMenuUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authr/menus")
@RequiredArgsConstructor
public class MenuCommandController {

    private final CreateMenuUseCase createMenuUseCase;
    private final UpdateMenuUseCase updateMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;
    private final MenuCommandWebMapper menuCommandWebMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CREATE')")
    public ResponseEntity<MenuCommandResponse> createMenu(@Valid @RequestBody MenuCommandRequest request) {
        MenuCommand command = menuCommandWebMapper.toCommand(request);
        MenuCommandResult result = createMenuUseCase.handle(command);
        URI location = URI.create("/api/authr/menus/" + result.menuId());
        return ResponseEntity.created(location)
                .body(menuCommandWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_UPDATE')")
    public ResponseEntity<MenuCommandResponse> updateMenu(
            @PathVariable Long id, @Valid @RequestBody MenuCommandRequest request) {
        MenuCommand command = menuCommandWebMapper.toCommand(request);
        MenuCommandResult result = updateMenuUseCase.handle(id, command);
        return ResponseEntity.ok(menuCommandWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        deleteMenuUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}
