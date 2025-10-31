package com.base.contexts.code.application.query.dto;

public record CodeQuery(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {}
