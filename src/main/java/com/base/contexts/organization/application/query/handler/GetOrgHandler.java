package com.base.contexts.organization.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.organization.application.query.dto.OrgQueryResult;
import com.base.contexts.organization.application.query.mapper.OrgQueryMapper;
import com.base.contexts.organization.application.query.port.in.GetOrgUseCase;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.port.out.OrgRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetOrgHandler implements GetOrgUseCase {

    private final OrgRepository orgRepository;
    private final OrgQueryMapper queryMapper;

    @Override
    public OrgQueryResult handle(Long orgId) {
        Org org = orgRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Org not found. id=" + orgId));
        return queryMapper.toResult(org);
    }
}
