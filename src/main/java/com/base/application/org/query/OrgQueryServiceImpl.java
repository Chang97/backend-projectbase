package com.base.application.org.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.org.dto.OrgResponse;
import com.base.api.org.mapper.OrgMapper;
import com.base.domain.org.OrgRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgQueryServiceImpl implements OrgQueryService {

    private final OrgRepository orgRepository;
    private final OrgMapper orgMapper;

    @Override
    @Transactional(readOnly = true)
    public OrgResponse getOrg(Long id) {
        return orgMapper.toResponse(orgRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Org not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgResponse> getOrgs() {
        return orgRepository.findAll().stream()
                .map(orgMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgResponse> getOrgsByUpperId(Long upperOrgId) {
        return orgRepository.findByUpperOrg_OrgIdAndUseYnTrueOrderBySrtAsc(upperOrgId).stream()
                .map(orgMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgResponse> getOrgsByUpperOrg(String upperOrg) {
        return orgRepository.findByUpperOrg_OrgCodeAndUseYnTrueOrderBySrtAsc(upperOrg).stream()
                .map(orgMapper::toResponse)
                .toList();
    }
}
