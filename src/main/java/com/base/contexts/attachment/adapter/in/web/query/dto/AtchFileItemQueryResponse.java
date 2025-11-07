package com.base.contexts.attachment.adapter.in.web.query.dto;

import java.time.LocalDateTime;

public record AtchFileItemQueryResponse(Long atchFileItemId,
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
