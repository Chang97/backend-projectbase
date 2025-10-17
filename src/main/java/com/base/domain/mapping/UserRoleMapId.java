package com.base.domain.mapping;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleMapId implements Serializable {

    private Long user;
    private Long role;

    public UserRoleMapId() {
    }

    public UserRoleMapId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleMapId that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}

