package com.base.contexts.organization.adapter.in.web.query.dto;

import java.time.OffsetDateTime;

public record OrgQueryResponse(Long orgId,
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
