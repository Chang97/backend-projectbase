package com.base.contexts.organization.domain.model;

import java.util.Objects;

public record OrgId(Long value) {

    public OrgId {
        Objects.requireNonNull(value, "OrgId value must not be null");
    }

    public static OrgId of(Long value) {
        return value == null ? null : new OrgId(value);
    }
}
