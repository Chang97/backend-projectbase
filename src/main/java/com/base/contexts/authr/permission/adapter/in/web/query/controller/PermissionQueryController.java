package com.base.contexts.authr.permission.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.authr.permission.adapter.in.web.query.dto.PermissionQueryRequest;
import com.base.contexts.authr.permission.adapter.in.web.query.dto.PermissionQueryResponse;
import com.base.contexts.authr.permission.adapter.in.web.query.mapper.PermissionQueryWebMapper;
import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.contexts.authr.permission.application.query.port.in.GetPermissionUseCase;
import com.base.contexts.authr.permission.application.query.port.in.GetPermissionsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionQueryController {
    
    private final GetPermissionUseCase getPermissionUseCase;
    private final GetPermissionsUseCase getPermissionsUseCase;
    private final PermissionQueryWebMapper permissionQueryWebMapper;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<PermissionQueryResponse> getPermission(@PathVariable Long id) {
        PermissionQueryResult result = getPermissionUseCase.handle(id);
        return ResponseEntity.ok(permissionQueryWebMapper.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_LIST')")
    public ResponseEntity<List<PermissionQueryResponse>> getPermissions(
            @ModelAttribute PermissionQueryRequest request
    ) {
        return ResponseEntity.ok(
                getPermissionsUseCase.handle(permissionQueryWebMapper.toQuery(request))
                        .stream()
                        .map(permissionQueryWebMapper::toResponse)
                        .toList()
        );
    }
}
