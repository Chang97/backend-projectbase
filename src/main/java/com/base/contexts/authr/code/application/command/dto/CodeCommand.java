package com.base.contexts.authr.code.application.command.dto;

public record CodeCommand(
        Long upperCodeId,
        String code,
        String codeName,
        String description,
        Integer srt,
        String etc1,
        String etc2,
        String etc3,
        String etc4,
        Boolean useYn
) {}
