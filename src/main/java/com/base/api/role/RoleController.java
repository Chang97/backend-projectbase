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

import com.base.api.role.assembler.RoleCommandAssembler;
import com.base.api.role.assembler.RoleResponseAssembler;
import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.application.role.port.in.CreateRoleUseCase;
import com.base.application.role.port.in.DeleteRoleUseCase;
import com.base.application.role.port.in.GetRoleUseCase;
import com.base.application.role.port.in.GetRolesUseCase;
import com.base.application.role.port.in.UpdateRoleUseCase;
import com.base.application.role.usecase.command.CreateRoleCommand;
import com.base.application.role.usecase.command.UpdateRoleCommand;
import com.base.application.role.usecase.query.condition.RoleSearchCondition;
import com.base.application.role.usecase.result.RoleResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final GetRoleUseCase getRoleUseCase;
    private final GetRolesUseCase getRolesUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final RoleCommandAssembler roleCommandAssembler;
    private final RoleResponseAssembler roleResponseAssembler;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIST')")
    public ResponseEntity<List<RoleResponse>> getRoles(@ModelAttribute RoleSearchCondition condition) {
        return ResponseEntity.ok(
                roleResponseAssembler.toResponses(
                        getRolesUseCase.handle(condition)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long id) {
        RoleResult result = getRoleUseCase.handle(id);
        return ResponseEntity.ok(roleResponseAssembler.toResponse(result));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        CreateRoleCommand command = roleCommandAssembler.toCreateCommand(request);
        RoleResult result = createRoleUseCase.handle(command);

        return ResponseEntity.ok(roleResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        UpdateRoleCommand command = roleCommandAssembler.toUpdateCommand(request);
        RoleResult result = updateRoleUseCase.handle(id, command);

        return ResponseEntity.ok(roleResponseAssembler.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        deleteRoleUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }
}
