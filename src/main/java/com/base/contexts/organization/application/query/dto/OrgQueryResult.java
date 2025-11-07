package com.base.contexts.organization.application.query.dto;

import java.time.LocalDateTime;

public record OrgQueryResult(Long orgId,
        Long upperOrgId,
        String orgCode,
        String orgName,
        Integer srt,
        Boolean useYn,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
