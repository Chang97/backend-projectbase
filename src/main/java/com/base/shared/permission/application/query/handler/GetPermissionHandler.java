package com.base.shared.permission.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NotFoundException;
import com.base.shared.permission.application.query.dto.PermissionQueryResult;
import com.base.shared.permission.application.query.mapper.PermissionQueryMapper;
import com.base.shared.permission.application.query.port.in.GetPermissionUseCase;
import com.base.shared.permission.domain.model.Permission;
import com.base.shared.permission.domain.port.out.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPermissionHandler implements GetPermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionQueryMapper permissionQueryMapper;

    @Override
    public PermissionQueryResult handle(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        return permissionQueryMapper.toResult(permission);
    }
}
