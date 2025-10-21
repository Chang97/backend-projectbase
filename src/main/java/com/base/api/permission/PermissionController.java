package com.base.api.permission;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;
import com.base.application.permission.command.PermissionCommandService;
import com.base.application.permission.query.PermissionQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {
    
    private final PermissionCommandService permissionCommandService;
    private final PermissionQueryService permissionQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionCommandService.createPermission(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable Long id, @RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionCommandService.updatePermission(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionCommandService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<PermissionResponse> getPermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionQueryService.getPermission(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_LIST')")
    public ResponseEntity<List<PermissionResponse>> getPermissions() {
        return ResponseEntity.ok(permissionQueryService.getPermissions());
    }
}
