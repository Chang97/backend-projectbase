package com.base.contexts.organization.adapter.in.web.query.dto;

public record OrgQueryRequest(Long upperOrgId,
        String upperOrgCode,
        String orgCode,
        String orgName,
        Boolean useYn) {
}
