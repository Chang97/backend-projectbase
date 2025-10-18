package com.base.api.menu;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.application.menu.command.MenuCommandService;
import com.base.application.menu.query.MenuQueryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuCommandService menuCommandService;
    private final MenuQueryService menuQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CREATE')")
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuCommandService.createMenu(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_UPDATE')")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long id, @RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuCommandService.updateMenu(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuCommandService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_READ')")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuQueryService.getMenu(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_LIST')")
    public ResponseEntity<List<MenuResponse>> getMenus() {
        return ResponseEntity.ok(menuQueryService.getMenus());
    }
}
