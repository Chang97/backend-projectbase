package com.base.api.org.dto;

public record OrgRequest(
        Long upperOrgId,
        String orgCode,
        String orgName,
        Integer srt,
        Boolean useYn
) {}
