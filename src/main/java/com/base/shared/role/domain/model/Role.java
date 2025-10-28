package com.base.shared.role.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {

    private final RoleId roleId;
    private String roleName;
    private Boolean useYn;

    public static Role create(String roleName,
            Boolean useYn) {
        return new Role(
                null,
                roleName,
                defaultUseYn(useYn)
        );
    }

    public static Role restore(RoleId roleId,
            String roleName,
            Boolean useYn) {
        return new Role(
                roleId,
                roleName,
                defaultUseYn(useYn)
        );
    }

    public void changeName(String newName) {
        this.roleName = newName;
    }

    public void enable() {
        this.useYn = true;
    }

    public void disable() {
        this.useYn = false;
    }

    private static Boolean defaultUseYn(Boolean useYn) {
        return useYn != null ? useYn : Boolean.TRUE;
    }
}
