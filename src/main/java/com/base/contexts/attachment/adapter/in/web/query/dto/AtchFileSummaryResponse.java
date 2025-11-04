package com.base.contexts.attachment.adapter.in.web.query.dto;

import java.time.OffsetDateTime;

public record AtchFileSummaryResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
