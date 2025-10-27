package com.base.identity.user.domain.model;

public record OrgId(Long value) {
    public static OrgId of (Long value) {
        return new OrgId(value);
    }
}