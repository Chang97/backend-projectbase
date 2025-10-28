package com.base.shared.role.adapter.in.web.query.dto;

import java.util.List;

public record RoleQueryResponse(
        Long roleId,
        String roleName,
        Boolean useYn,
        List<Long> permissionIds
) {}
