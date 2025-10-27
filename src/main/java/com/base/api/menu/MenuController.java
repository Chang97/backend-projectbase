package com.base.api.menu;

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
import org.springframework.web.bind.annotation.RestController;

import com.base.api.menu.assembler.MenuCommandAssembler;
import com.base.api.menu.assembler.MenuResponseAssembler;
import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.application.menu.usecase.create.CreateMenuUseCase;
import com.base.application.menu.usecase.delete.DeleteMenuUseCase;
import com.base.application.menu.usecase.query.condition.MenuSearchCondition;
import com.base.application.menu.usecase.query.detail.GetMenuUseCase;
import com.base.application.menu.usecase.query.list.GetMenusUseCase;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.application.menu.usecase.update.UpdateMenuUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    
    private final CreateMenuUseCase createMenuUseCase;
    private final UpdateMenuUseCase updateMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;
    private final GetMenuUseCase getMenuUseCase;
    private final GetMenusUseCase getMenusUseCase;
    private final MenuCommandAssembler menuCommandAssembler;
    private final MenuResponseAssembler menuResponseAssembler;

    @PostMapping
    @PreAuthorize("hasAuthority('MENU_CREATE')")
    public ResponseEntity<MenuResponse> createMenu(@Valid @RequestBody MenuRequest request) {
        MenuResult result = createMenuUseCase.handle(menuCommandAssembler.toCreateCommand(request));
        return ResponseEntity.ok(menuResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_UPDATE')")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long id, @Valid @RequestBody MenuRequest request) {
        MenuResult result = updateMenuUseCase.handle(id, menuCommandAssembler.toUpdateCommand(request));
        return ResponseEntity.ok(menuResponseAssembler.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_DELETE')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        deleteMenuUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_READ')")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable Long id) {
        MenuResult result = getMenuUseCase.handle(id);
        return ResponseEntity.ok(menuResponseAssembler.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_LIST')")
    public ResponseEntity<List<MenuResponse>> getMenus(@ModelAttribute MenuSearchCondition condition) {
        return ResponseEntity.ok(
                menuResponseAssembler.toResponses(getMenusUseCase.handle(condition))
        );
    }
}
