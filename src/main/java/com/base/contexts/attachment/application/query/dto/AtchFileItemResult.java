package com.base.contexts.attachment.application.query.dto;

import java.time.LocalDateTime;

public record AtchFileItemResult(Long atchFileItemId,
        Long atchFileId,
        String path,
        String fileName,
        Long fileSize,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
