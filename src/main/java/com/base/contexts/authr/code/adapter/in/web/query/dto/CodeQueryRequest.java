package com.base.contexts.authr.code.adapter.in.web.query.dto;

public record CodeQueryRequest(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {}
