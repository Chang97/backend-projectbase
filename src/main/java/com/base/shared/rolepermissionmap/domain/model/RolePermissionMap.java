package com.base.shared.rolepermissionmap.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RolePermissionMap {

    private final RolePermissionMapId id;
    private final Long roleId;
    private final Long permissionId;

    public static RolePermissionMap of(Long roleId, Long permissionId) {
        RolePermissionMapId rolePermissionId = RolePermissionMapId.of(roleId, permissionId);
        return new RolePermissionMap(rolePermissionId, roleId, permissionId);
    }
}
