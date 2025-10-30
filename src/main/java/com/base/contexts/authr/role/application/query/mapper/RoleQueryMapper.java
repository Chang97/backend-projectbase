package com.base.contexts.authr.role.application.query.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.role.application.query.dto.RoleQuery;
import com.base.contexts.authr.role.application.query.dto.RoleQueryResult;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.model.RoleFilter;
import com.base.shared.core.util.StringNormalizer;
import java.util.List;

@Component
public class RoleQueryMapper {

    public RoleFilter toFilter(RoleQuery query) {
        if (query == null) {
            return RoleFilter.empty();
        }
        return new RoleFilter(
                query.roleId(),
                StringNormalizer.trimToNull(query.roleName()),
                query.useYn()
        );
    }

    public RoleQueryResult toResult(Role role, List<Long> permissionIds) {
        return new RoleQueryResult(
                role.getRoleId() != null ? role.getRoleId().value() : null,
                role.getRoleName(),
                role.getUseYn(),
                permissionIds
        );
    }
}
