package com.base.contexts.organization.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.organization.adapter.in.web.command.dto.OrgCommandRequest;
import com.base.contexts.organization.adapter.in.web.command.dto.OrgCommandResponse;
import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;

@Component
public class OrgCommandWebMapper {

    public OrgCommand toCommand(OrgCommandRequest request) {
        return new OrgCommand(
                request.orgCode(),
                request.orgName(),
                request.upperOrgId(),
                request.srt(),
                request.useYn()
        );
    }

    public OrgCommandResponse toResponse(OrgCommandResult result) {
        return new OrgCommandResponse(
                result.orgId(),
                result.upperOrgId(),
                result.orgCode(),
                result.orgName(),
                result.srt(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
