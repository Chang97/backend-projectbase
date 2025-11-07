package com.base.contexts.organization.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.contexts.organization.application.query.dto.OrgQuery;
import com.base.contexts.organization.application.query.dto.OrgQueryResult;
import com.base.contexts.organization.application.query.mapper.OrgQueryMapper;
import com.base.contexts.organization.application.query.port.in.GetOrgsUseCase;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.port.out.OrgQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetOrgsHandler implements GetOrgsUseCase {

    private final OrgQueryPort orgQueryPort;
    private final OrgQueryMapper queryMapper;

    @Override
    public List<OrgQueryResult> handle(OrgQuery query) {
        List<Org> orgs = fetchOrgs(query);
        return orgs.stream()
                .filter(org -> filterByCode(org, query))
                .filter(org -> filterByName(org, query))
                .filter(org -> filterByUseYn(org, query))
                .map(queryMapper::toResult)
                .toList();
    }

    private List<Org> fetchOrgs(OrgQuery query) {
        if (query == null) {
            return orgQueryPort.findAll();
        }
        if (query.upperOrgId() != null) {
            return orgQueryPort.findByUpperOrgId(query.upperOrgId());
        }
        if (StringUtils.hasText(query.upperOrgCode())) {
            return orgQueryPort.findByUpperOrgCode(query.upperOrgCode().trim());
        }
        return orgQueryPort.findAll();
    }

    private boolean filterByCode(Org org, OrgQuery query) {
        if (query == null || !StringUtils.hasText(query.orgCode())) {
            return true;
        }
        return query.orgCode().trim().equalsIgnoreCase(org.getOrgCode());
    }

    private boolean filterByName(Org org, OrgQuery query) {
        if (query == null || !StringUtils.hasText(query.orgName())) {
            return true;
        }
        String name = org.getOrgName() != null ? org.getOrgName() : "";
        return name.toLowerCase().contains(query.orgName().trim().toLowerCase());
    }

    private boolean filterByUseYn(Org org, OrgQuery query) {
        if (query == null || query.useYn() == null) {
            return true;
        }
        return query.useYn().equals(Boolean.TRUE.equals(org.getUseYn()));
    }
}
