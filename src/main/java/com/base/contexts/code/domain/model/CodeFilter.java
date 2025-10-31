package com.base.contexts.code.domain.model;

public record CodeFilter(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {
    public static CodeFilter empty() {
        return new CodeFilter(null, null, null, null, null);
    }
}
