package com.base.shared.code.domain.model;

public record CodeId(Long codeId) {
    public static CodeId of(Long codeId) {
        return codeId == null ? null : new CodeId(codeId);
    }
}
