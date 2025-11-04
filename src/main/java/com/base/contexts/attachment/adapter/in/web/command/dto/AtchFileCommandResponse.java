package com.base.contexts.attachment.adapter.in.web.command.dto;

import java.time.OffsetDateTime;

public record AtchFileCommandResponse(Long atchFileId,
        Long fileGroupCodeId,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
