package com.base.contexts.organization.adapter.in.web.command.dto;

import java.time.LocalDateTime;

public record OrgCommandResponse(Long orgId,
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
