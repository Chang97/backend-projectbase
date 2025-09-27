package com.base.api.org.dto;

public record OrgResponse(
        Long orgId,
        Long upperOrgId,
        String orgCode,
        String orgName,
        Integer srt,
        Boolean useYn
) {}
