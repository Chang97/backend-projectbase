package com.base.contexts.attachment.domain.model;

import java.util.Objects;

public record AtchFileId(Long value) {

    public AtchFileId {
        Objects.requireNonNull(value, "AtchFileId value must not be null");
    }

    public static AtchFileId of(Long value) {
        return value == null ? null : new AtchFileId(value);
    }
}
