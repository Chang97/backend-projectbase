package com.base.contexts.authr.permission.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission {

    private PermissionId permissionId;
    private String permissionCode;
    private String permissionName;
    private Boolean useYn;

    public static Permission create(String permissionCode,
            String permissionName,
            Boolean useYn) {
        return new Permission(
                null,
                permissionCode,
                permissionName,
                useYn != null ? useYn : Boolean.TRUE
        );
    }

    public static Permission restore(PermissionId permissionId,
            String permissionCode,
            String permissionName,
            Boolean useYn) {
        return new Permission(
                permissionId,
                permissionCode,
                permissionName,
                useYn != null ? useYn : Boolean.TRUE
        );
    }

    public void changeName(String newName) {
        this.permissionName = newName;
    }

    public void enable() {
        this.useYn = true;
    }

    public void disable() {
        this.useYn = false;
    }
}
