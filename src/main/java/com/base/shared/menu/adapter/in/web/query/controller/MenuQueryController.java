package com.base.shared.menu.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.shared.menu.adapter.in.web.query.dto.MenuQueryRequest;
import com.base.shared.menu.adapter.in.web.query.dto.MenuQueryResponse;
import com.base.shared.menu.adapter.in.web.query.mapper.MenuQueryWebMapper;
import com.base.shared.menu.application.query.dto.MenuQuery;
import com.base.shared.menu.application.query.dto.MenuQueryResult;
import com.base.shared.menu.application.query.port.in.GetMenuUseCase;
import com.base.shared.menu.application.query.port.in.GetMenusUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuQueryController {

    private final GetMenuUseCase getMenuUseCase;
    private final GetMenusUseCase getMenusUseCase;
    private final MenuQueryWebMapper menuQueryWebMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MENU_READ')")
    public ResponseEntity<MenuQueryResponse> getMenu(@PathVariable Long id) {
        MenuQueryResult result = getMenuUseCase.handle(id);
        return ResponseEntity.ok(menuQueryWebMapper.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MENU_LIST')")
    public ResponseEntity<List<MenuQueryResponse>> getMenus(@ModelAttribute MenuQueryRequest request) {
        MenuQuery query = menuQueryWebMapper.toQuery(request);
        return ResponseEntity.ok(menuQueryWebMapper.toResponses(getMenusUseCase.handle(query)));
    }
}
