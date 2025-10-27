package com.base.shared.code.adapter.in.web.query.dto;

public record CodeQueryRequest(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {}
