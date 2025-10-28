package com.base.shared.role.domain.model;

public record RoleId(Long value) {

    public static RoleId of(Long value) {
        return value == null ? null : new RoleId(value);
    }
}
