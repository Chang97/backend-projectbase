package com.base.contexts.attachment.application.query.dto;

import java.time.LocalDateTime;

public record AtchFileSummaryResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
