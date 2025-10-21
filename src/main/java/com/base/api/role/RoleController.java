package com.base.api.role;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.application.role.command.RoleCommandService;
import com.base.application.role.query.RoleQueryService;
import com.base.application.role.query.RoleSearchCondition;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleCommandService roleCommandService;
    private final RoleQueryService roleQueryService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIST')")
    public ResponseEntity<List<RoleResponse>> getRoles(@ModelAttribute RoleSearchCondition condition) {
        return ResponseEntity.ok(roleQueryService.getRoles(condition));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleQueryService.getRole(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleCommandService.createRole(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleCommandService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleCommandService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
