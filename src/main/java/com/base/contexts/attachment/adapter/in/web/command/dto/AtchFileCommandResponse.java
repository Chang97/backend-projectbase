package com.base.contexts.attachment.adapter.in.web.command.dto;

import java.time.LocalDateTime;

public record AtchFileCommandResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
