package com.base.contexts.organization.application.command.dto;

import java.time.LocalDateTime;

public record OrgCommandResult(Long orgId,
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
