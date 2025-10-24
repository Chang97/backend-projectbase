package com.base.application.role.usecase.result;

import java.util.List;

public record RoleResult(
    Long roleId,
    String roleName,
    Boolean useYn,
    List<Long> permissionIds
) {}