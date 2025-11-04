package com.base.contexts.organization.application.query.dto;

import java.time.OffsetDateTime;

public record OrgQueryResult(Long orgId,
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
