package com.base.application.org.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.org.dto.OrgRequest;
import com.base.api.org.dto.OrgResponse;
import com.base.api.org.mapper.OrgMapper;
import com.base.domain.org.Org;
import com.base.domain.org.OrgRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrgCommandServiceImpl implements OrgCommandService {

    private final OrgRepository orgRepository;
    private final OrgMapper orgMapper;

    @Override
    @Transactional
    public OrgResponse createOrg(OrgRequest request) {
        if (orgRepository.existsByOrgCode(request.orgCode())) {
            throw new ConflictException("Org code already exists: " + request.orgCode());
        }
        Org org = orgMapper.toEntity(request);
        return orgMapper.toResponse(orgRepository.save(org));
    }

    @Override
    @Transactional
    public OrgResponse updateOrg(Long id, OrgRequest request) {
        Org existing = orgRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Org not found"));

        if (!existing.getOrgCode().equals(request.orgCode())
                && orgRepository.existsByOrgCode(request.orgCode())) {
            throw new ConflictException("Org code already exists: " + request.orgCode());
        }

        orgMapper.updateFromRequest(request, existing);
        return orgMapper.toResponse(orgRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteOrg(Long id) {
        Org existing = orgRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Org not found"));
        existing.setUseYn(false); // soft delete
        orgRepository.save(existing);
    }

    
}
