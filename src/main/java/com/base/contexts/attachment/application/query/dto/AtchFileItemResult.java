package com.base.contexts.attachment.application.query.dto;

import java.time.OffsetDateTime;

public record AtchFileItemResult(Long atchFileItemId,
        Long atchFileId,
        String path,
        String fileName,
        Long fileSize,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
