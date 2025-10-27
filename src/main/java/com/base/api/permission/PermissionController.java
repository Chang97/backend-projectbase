package com.base.api.permission;

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

import com.base.api.permission.assembler.PermissionCommandAssembler;
import com.base.api.permission.assembler.PermissionResponseAssembler;
import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;
import com.base.application.permission.usecase.create.CreatePermissionUseCase;
import com.base.application.permission.usecase.delete.DeletePermissionUseCase;
import com.base.application.permission.usecase.query.condition.PermissionSearchCondition;
import com.base.application.permission.usecase.query.detail.GetPermissionUseCase;
import com.base.application.permission.usecase.query.list.GetPermissionsUseCase;
import com.base.application.permission.usecase.result.PermissionResult;
import com.base.application.permission.usecase.update.UpdatePermissionUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {
    
    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;
    private final GetPermissionUseCase getPermissionUseCase;
    private final GetPermissionsUseCase getPermissionsUseCase;
    private final PermissionCommandAssembler permissionCommandAssembler;
    private final PermissionResponseAssembler permissionResponseAssembler;

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        PermissionResult result = createPermissionUseCase.handle(permissionCommandAssembler.toCreateCommand(request));
        return ResponseEntity.ok(permissionResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable Long id, @RequestBody PermissionRequest request) {
        PermissionResult result = updatePermissionUseCase.handle(id, permissionCommandAssembler.toUpdateCommand(request));
        return ResponseEntity.ok(permissionResponseAssembler.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        deletePermissionUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<PermissionResponse> getPermission(@PathVariable Long id) {
        PermissionResult result = getPermissionUseCase.handle(id);
        return ResponseEntity.ok(permissionResponseAssembler.toResponse(result));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_LIST')")
    public ResponseEntity<List<PermissionResponse>> getPermissions(
            @ModelAttribute PermissionSearchCondition condition
    ) {
        return ResponseEntity.ok(
                permissionResponseAssembler.toResponses(getPermissionsUseCase.handle(condition))
        );
    }
}
