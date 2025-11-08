package com.base.contexts.authr.permission.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.permission.application.query.dto.PermissionQuery;
import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.contexts.authr.permission.application.query.mapper.PermissionQueryMapper;
import com.base.contexts.authr.permission.application.query.port.in.GetPermissionsUseCase;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;
import com.base.contexts.authr.permission.domain.port.out.PermissionQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPermissionsHandler implements GetPermissionsUseCase {

    private final PermissionQueryPort permissionQueryPort;
    private final PermissionQueryMapper permissionQueryMapper;

    @Override
    public List<PermissionQueryResult> handle(PermissionQuery query) {
        
        PermissionFilter filter = permissionQueryMapper.toFilter(query);

        return permissionQueryPort.findAll(filter).stream()
                .map(permissionQueryMapper::toResult)
                .toList();
    }
}
