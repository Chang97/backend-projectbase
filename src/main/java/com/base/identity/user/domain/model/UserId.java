package com.base.identity.user.domain.model;

public record UserId(Long value) {

    public static UserId of(Long value) {
        return value == null ? null : new UserId(value);
    }
}
