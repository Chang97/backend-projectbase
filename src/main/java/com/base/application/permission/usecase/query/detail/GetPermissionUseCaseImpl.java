package com.base.application.permission.usecase.query.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.permission.usecase.query.assembler.PermissionResultAssembler;
import com.base.application.permission.usecase.result.PermissionResult;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPermissionUseCaseImpl implements GetPermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionResultAssembler permissionResultAssembler;

    @Override
    public PermissionResult handle(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        return permissionResultAssembler.toResult(permission);
    }
}
