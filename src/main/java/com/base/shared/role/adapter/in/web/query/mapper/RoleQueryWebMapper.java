package com.base.shared.role.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.shared.core.util.StringNormalizer;
import com.base.shared.role.adapter.in.web.query.dto.RoleQueryRequest;
import com.base.shared.role.adapter.in.web.query.dto.RoleQueryResponse;
import com.base.shared.role.application.query.dto.RoleQuery;
import com.base.shared.role.application.query.dto.RoleQueryResult;

@Component
public class RoleQueryWebMapper {

    public RoleQuery toQuery(RoleQueryRequest request) {
        if (request == null) {
            return new RoleQuery(null, null, null);
        }
        return new RoleQuery(
                request.roleId(),
                StringNormalizer.trimToNull(request.roleName()),
                request.useYn()
        );
    }

    public RoleQueryResponse toResponse(RoleQueryResult result) {
        return new RoleQueryResponse(
                result.roleId(),
                result.roleName(),
                result.useYn(),
                result.permissionIds()
        );
    }

    public List<RoleQueryResponse> toResponses(List<RoleQueryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
