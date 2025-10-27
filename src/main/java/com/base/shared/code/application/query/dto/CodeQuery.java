package com.base.shared.code.application.query.dto;

public record CodeQuery(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {
    public CodeQuery normalize() {
        return new CodeQuery(
                upperCodeId,
                upperCode != null ? upperCode.trim() : null,
                code != null ? code.trim() : null,
                codeName != null ? codeName.trim() : null,
                useYn
        );
    }
}
