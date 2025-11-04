package com.base.contexts.attachment.application.query.dto;

import java.time.OffsetDateTime;

public record AtchFileSummaryResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
