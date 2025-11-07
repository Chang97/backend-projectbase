package com.base.contexts.attachment.application.command.dto;

import java.time.LocalDateTime;

public record AtchFileCommandResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
