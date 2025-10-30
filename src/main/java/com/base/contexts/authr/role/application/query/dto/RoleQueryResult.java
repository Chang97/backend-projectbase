package com.base.contexts.authr.role.application.query.dto;

import java.util.List;

public record RoleQueryResult(
        Long roleId,
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
