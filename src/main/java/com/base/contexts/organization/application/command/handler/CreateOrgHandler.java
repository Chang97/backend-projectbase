package com.base.contexts.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;
import com.base.contexts.organization.application.command.mapper.OrgCommandMapper;
import com.base.contexts.organization.application.command.port.in.CreateOrgUseCase;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.port.out.OrgRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateOrgHandler implements CreateOrgUseCase {

    private final OrgRepository orgRepository;
    private final OrgCommandMapper commandMapper;

    @Override
    public OrgCommandResult handle(OrgCommand command) {
        if (orgRepository.existsByOrgCode(command.orgCode())) {
            throw new ConflictException("Org code already exists: " + command.orgCode());
        }
        if (command.upperOrgId() != null) {
            orgRepository.findById(command.upperOrgId())
                    .orElseThrow(() -> new NotFoundException("Parent org not found. id=" + command.upperOrgId()));
        }

        Org org = commandMapper.toDomain(command);
        Org saved = orgRepository.save(org);
        return commandMapper.toResult(saved);
    }
}
