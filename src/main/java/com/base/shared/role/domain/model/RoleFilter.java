package com.base.shared.role.domain.model;

public record RoleFilter(
        Long roleId,
        String roleName,
        Boolean useYn
) {
    public static RoleFilter empty() {
        return new RoleFilter(null, null, null);
    }
}
