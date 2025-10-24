package com.base.api.role.dto;

import java.util.List;

public record RoleResponse(
        Long roleId,
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
