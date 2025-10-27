package com.base.application.role.usecase.query.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.role.port.in.GetRoleUseCase;
import com.base.application.role.port.out.RolePersistencePort;
import com.base.application.role.port.out.RolePermissionPort;
import com.base.application.role.usecase.query.assembler.RoleResultAssembler;
import com.base.application.role.usecase.result.RoleResult;
import com.base.domain.role.Role;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetRoleUseCaseImpl implements GetRoleUseCase {

    private final RolePersistencePort rolePersistencePort;
    private final RolePermissionPort rolePermissionPort;
    private final RoleResultAssembler roleResultAssembler;

    @Override
    public RoleResult handle(Long roleId) {
        Role role = rolePersistencePort.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        return roleResultAssembler.toResult(
                role,
                rolePermissionPort.findPermissionIdsByRoleId(roleId)
        );
    }
}
