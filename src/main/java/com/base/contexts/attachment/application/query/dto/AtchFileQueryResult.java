package com.base.contexts.attachment.application.query.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record AtchFileQueryResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<AtchFileItemResult> items) {
}
