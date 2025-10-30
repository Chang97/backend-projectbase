package com.base.contexts.authr.permission.application.query.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.permission.application.query.dto.PermissionQuery;
import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;

@Component
public class PermissionQueryMapper {
    public PermissionFilter toFilter(PermissionQuery query) {
        PermissionQuery effective = query == null
                ? new PermissionQuery(null, null, null, null)
                : query;

        return new PermissionFilter(
                effective.permissionId(),
                effective.permissionCode(),
                effective.permissionName(),
                effective.useYn()
        );
    }

    public PermissionQueryResult toResult(Permission domain) {
        return new PermissionQueryResult(
            domain.getPermissionId().permissionId(),
            domain.getPermissionCode(),
            domain.getPermissionName(),
            domain.getUseYn()
        );
    }
}
