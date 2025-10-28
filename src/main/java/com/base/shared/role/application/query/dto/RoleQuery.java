package com.base.shared.role.application.query.dto;

public record RoleQuery(
        Long roleId,
        String roleName,
        Boolean useYn
) {}
