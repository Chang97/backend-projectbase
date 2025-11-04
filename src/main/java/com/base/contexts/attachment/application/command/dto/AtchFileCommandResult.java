package com.base.contexts.attachment.application.command.dto;

import java.time.OffsetDateTime;

public record AtchFileCommandResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
