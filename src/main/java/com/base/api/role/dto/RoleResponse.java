package com.base.api.role.dto;

import java.time.OffsetDateTime;

public record RoleResponse(
        Long roleId,
        String roleName,
        Boolean useYn,
        OffsetDateTime createdDt,
        OffsetDateTime updatedDt
) {}
