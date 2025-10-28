package com.base.shared.role.application.query.dto;

import java.util.List;

public record RoleQueryResult(
        Long roleId,
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
