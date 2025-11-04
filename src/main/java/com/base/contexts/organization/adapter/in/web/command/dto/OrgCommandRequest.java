package com.base.contexts.organization.adapter.in.web.command.dto;

import jakarta.validation.constraints.NotBlank;

public record OrgCommandRequest(
        @NotBlank String orgCode,
        String orgName,
        Long upperOrgId,
        Integer srt,
        Boolean useYn) {
}
