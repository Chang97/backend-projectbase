package com.base.api.role.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record RoleResponse(
        Long roleId,
        String roleName,
        Boolean useYn,
        OffsetDateTime createdDt,
        OffsetDateTime updatedDt,
        List<Long> permissionIds
) {}
