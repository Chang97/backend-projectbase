package com.base.contexts.authr.role.domain.model;

public record RoleId(Long value) {

    public static RoleId of(Long value) {
        return value == null ? null : new RoleId(value);
    }
}
