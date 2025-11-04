package com.base.contexts.organization.application.query.dto;

public record OrgQuery(Long upperOrgId,
        String upperOrgCode,
        String orgCode,
        String orgName,
        Boolean useYn) {
}
