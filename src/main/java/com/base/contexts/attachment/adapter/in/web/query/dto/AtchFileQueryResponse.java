package com.base.contexts.attachment.adapter.in.web.query.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record AtchFileQueryResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<AtchFileItemQueryResponse> items) {
}
