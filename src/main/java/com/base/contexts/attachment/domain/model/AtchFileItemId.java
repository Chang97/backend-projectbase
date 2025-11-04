package com.base.contexts.attachment.domain.model;

import java.util.Objects;

public record AtchFileItemId(Long value) {

    public AtchFileItemId {
        Objects.requireNonNull(value, "AtchFileItemId value must not be null");
    }

    public static AtchFileItemId of(Long value) {
        return value == null ? null : new AtchFileItemId(value);
    }
}
