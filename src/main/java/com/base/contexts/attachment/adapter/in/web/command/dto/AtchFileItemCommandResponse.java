package com.base.contexts.attachment.adapter.in.web.command.dto;

import java.time.LocalDateTime;

public record AtchFileItemCommandResponse(Long atchFileItemId,
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
