package com.base.identity.user.domain.model;

import java.util.Objects;

public final class UserRole {

    private final RoleId roleId;
    private final String roleName;

    private UserRole(RoleId roleId, String roleName) {
        this.roleId = Objects.requireNonNull(roleId);
        this.roleName = roleName;
    }

    public static UserRole of(RoleId roleId, String roleName) {
        return new UserRole(roleId, roleName);
    }

    public RoleId id() {
        return roleId;
    }

    public String name() {
        return roleName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UserRole other) && roleId.equals(other.roleId);
    }

    @Override
    public int hashCode() {
        return roleId.hashCode();
    }

    @Override
    public String toString() {
        return "UserRole(" + roleName + ")";
    }
}
