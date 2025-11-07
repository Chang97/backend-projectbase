package com.base.contexts.attachment.adapter.in.web.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AtchFileQueryResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<AtchFileItemQueryResponse> items) {
}
