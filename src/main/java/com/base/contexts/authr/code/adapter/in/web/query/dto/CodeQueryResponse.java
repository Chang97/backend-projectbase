package com.base.contexts.authr.code.adapter.in.web.query.dto;

public record CodeQueryResponse(
        Long codeId,
        Long upperCodeId,
        String code,
        String codeName,
        String description,
        Integer srt,
        String orderPath,
        String etc1,
        String etc2,
        String etc3,
        String etc4,
        Boolean useYn
) {}
