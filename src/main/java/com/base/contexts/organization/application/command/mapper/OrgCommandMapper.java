package com.base.contexts.organization.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.model.OrgId;

@Component
public class OrgCommandMapper {

    public Org toDomain(OrgCommand command) {
        return Org.create(
                command.orgCode(),
                command.orgName(),
                command.srt(),
                OrgId.of(command.upperOrgId()),
                command.useYn()
        );
    }

    public OrgCommandResult toResult(Org org) {
        return new OrgCommandResult(
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
