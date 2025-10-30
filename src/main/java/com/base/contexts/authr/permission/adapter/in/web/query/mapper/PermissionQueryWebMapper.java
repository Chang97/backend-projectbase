package com.base.contexts.authr.permission.adapter.in.web.query.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.permission.adapter.in.web.query.dto.PermissionQueryRequest;
import com.base.contexts.authr.permission.adapter.in.web.query.dto.PermissionQueryResponse;
import com.base.contexts.authr.permission.application.query.dto.PermissionQuery;
import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.shared.core.util.StringNormalizer;

@Component
public class PermissionQueryWebMapper {

    public PermissionQuery toQuery(PermissionQueryRequest request) {
        return new PermissionQuery(
                request.permissionId(),
                StringNormalizer.trimToNull(request.permissionCode()),
                StringNormalizer.trimToNull(request.permissionName()),
                request.useYn()
        );
    }

    public PermissionQueryResponse toResponse(PermissionQueryResult result) {
        return new PermissionQueryResponse(
            result.permissionId(),
            result.permissionCode(),
            result.permissionName(),
            result.useYn()
            );
    }
}
