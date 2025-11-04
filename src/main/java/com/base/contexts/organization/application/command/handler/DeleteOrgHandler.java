package com.base.contexts.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.organization.application.command.port.in.DeleteOrgUseCase;
import com.base.contexts.organization.domain.port.out.OrgRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteOrgHandler implements DeleteOrgUseCase {

    private final OrgRepository orgRepository;

    @Override
    public void handle(Long orgId) {
        boolean exists = orgRepository.findById(orgId).isPresent();
        if (!exists) {
            throw new NotFoundException("Org not found. id=" + orgId);
        }
        orgRepository.deleteById(orgId);
    }
}
