package com.base.application.code.usecase.command;

public record UpdateCodeCommand(
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
