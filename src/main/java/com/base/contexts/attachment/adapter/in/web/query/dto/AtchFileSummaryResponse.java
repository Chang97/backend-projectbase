package com.base.contexts.attachment.adapter.in.web.query.dto;

import java.time.LocalDateTime;

public record AtchFileSummaryResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
