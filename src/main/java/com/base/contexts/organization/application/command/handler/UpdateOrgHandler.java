package com.base.contexts.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;
import com.base.contexts.organization.application.command.mapper.OrgCommandMapper;
import com.base.contexts.organization.application.command.port.in.UpdateOrgUseCase;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.model.OrgId;
import com.base.contexts.organization.domain.port.out.OrgRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateOrgHandler implements UpdateOrgUseCase {

    private final OrgRepository orgRepository;
    private final OrgCommandMapper commandMapper;

    @Override
    public OrgCommandResult handle(Long orgId, OrgCommand command) {
        Org existing = orgRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Org not found. id=" + orgId));

        if (!existing.getOrgCode().equals(command.orgCode())
                && orgRepository.existsByOrgCode(command.orgCode())) {
            throw new ConflictException("Org code already exists: " + command.orgCode());
        }

        if (command.upperOrgId() != null) {
            orgRepository.findById(command.upperOrgId())
                    .orElseThrow(() -> new NotFoundException("Parent org not found. id=" + command.upperOrgId()));
        }

        existing.changeOrgCode(command.orgCode());
        existing.changeOrgName(command.orgName());
        existing.changeSrt(command.srt());
        existing.changeUseYn(command.useYn());
        existing.attachTo(OrgId.of(command.upperOrgId()));

        Org saved = orgRepository.save(existing);
        return commandMapper.toResult(saved);
    }
}
