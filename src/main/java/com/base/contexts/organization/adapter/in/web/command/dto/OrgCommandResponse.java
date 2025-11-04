package com.base.contexts.organization.adapter.in.web.command.dto;

import java.time.OffsetDateTime;

public record OrgCommandResponse(Long orgId,
        Long upperOrgId,
        String orgCode,
        String orgName,
        Integer srt,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
