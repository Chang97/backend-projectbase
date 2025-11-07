package com.base.contexts.attachment.application.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AtchFileQueryResult(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<AtchFileItemResult> items) {
}
