package com.base.shared.role.adapter.in.web.query.dto;

public record RoleQueryRequest(
        Long roleId,
        String roleName,
        Boolean useYn
) {}
