package com.base.shared.role.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.shared.role.adapter.in.web.query.dto.RoleQueryRequest;
import com.base.shared.role.adapter.in.web.query.dto.RoleQueryResponse;
import com.base.shared.role.adapter.in.web.query.mapper.RoleQueryWebMapper;
import com.base.shared.role.application.query.dto.RoleQuery;
import com.base.shared.role.application.query.dto.RoleQueryResult;
import com.base.shared.role.application.query.port.in.GetRoleUseCase;
import com.base.shared.role.application.query.port.in.GetRolesUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleQueryController {

    private final GetRoleUseCase getRoleUseCase;
    private final GetRolesUseCase getRolesUseCase;
    private final RoleQueryWebMapper roleQueryWebMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleQueryResponse> getRole(@PathVariable Long id) {
        RoleQueryResult result = getRoleUseCase.handle(id);
        return ResponseEntity.ok(roleQueryWebMapper.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIST')")
    public ResponseEntity<List<RoleQueryResponse>> getRoles(@ModelAttribute RoleQueryRequest request) {
        RoleQuery query = roleQueryWebMapper.toQuery(request);
        return ResponseEntity.ok(roleQueryWebMapper.toResponses(getRolesUseCase.handle(query)));
    }
}
