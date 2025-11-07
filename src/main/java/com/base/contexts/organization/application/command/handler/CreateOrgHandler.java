package com.base.contexts.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;
import com.base.contexts.organization.application.command.mapper.OrgCommandMapper;
import com.base.contexts.organization.application.command.port.in.CreateOrgUseCase;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.model.OrgId;
import com.base.contexts.organization.domain.policy.OrgPolicy;
import com.base.contexts.organization.domain.port.out.OrgCommandPort;
import com.base.platform.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateOrgHandler implements CreateOrgUseCase {

    private final OrgCommandPort orgCommandPort;
    private final OrgCommandMapper commandMapper;

    @Override
    public OrgCommandResult handle(OrgCommand command) {
        if (orgCommandPort.existsByOrgCode(command.orgCode())) {
            throw new ConflictException("Org code already exists: " + command.orgCode());
        }

        Org org = commandMapper.toDomain(command);
        OrgPolicy.using(this::orgExists).assertUpperExists(org.getUpperOrgId());
        Org saved = orgCommandPort.save(org);
        return commandMapper.toResult(saved);
    }

    private Boolean orgExists(OrgId orgId) {
        return orgId != null && orgCommandPort.existsById(orgId.value());
    }
}
