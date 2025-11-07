package com.base.contexts.authr.permission.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.contexts.authr.permission.application.query.mapper.PermissionQueryMapper;
import com.base.contexts.authr.permission.application.query.port.in.GetPermissionUseCase;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.port.out.PermissionQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPermissionHandler implements GetPermissionUseCase {

    private final PermissionQueryPort permissionQueryPort;
    private final PermissionQueryMapper permissionQueryMapper;

    @Override
    public PermissionQueryResult handle(Long permissionId) {
        Permission permission = permissionQueryPort.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        return permissionQueryMapper.toResult(permission);
    }
}
