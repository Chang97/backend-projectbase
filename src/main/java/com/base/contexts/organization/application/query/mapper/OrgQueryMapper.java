package com.base.contexts.organization.application.query.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.organization.application.query.dto.OrgQueryResult;
import com.base.contexts.organization.domain.model.Org;

@Component
public class OrgQueryMapper {

    public OrgQueryResult toResult(Org org) {
        return new OrgQueryResult(
                org.getOrgId() != null ? org.getOrgId().value() : null,
                org.getUpperOrgId() != null ? org.getUpperOrgId().value() : null,
                org.getOrgCode(),
                org.getOrgName(),
                org.getSrt(),
                org.getUseYn(),
                org.getCreatedBy(),
                org.getUpdatedBy(),
                org.getCreatedAt(),
                org.getUpdatedAt()
        );
    }
}
