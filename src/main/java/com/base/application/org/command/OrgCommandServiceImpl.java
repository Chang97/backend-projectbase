package com.base.application.org.command;

import org.springframework.stereotype.Service;

import com.base.domain.org.Org;
import com.base.domain.org.OrgRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrgCommandServiceImpl implements OrgCommandService {

    private final OrgRepository orgRepository;

    @Override
    public Org createOrg(Org org) {
        return orgRepository.save(org);
    }

    @Override
    public Org updateOrg(Long id, Org org) {
        Org existing = orgRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Org not found"));
        existing.setUpperOrg(org.getUpperOrg());
        existing.setOrgName(org.getOrgName());
        existing.setSrt(org.getSrt());
        existing.setUseYn(org.getUseYn());
        return orgRepository.save(existing);
    }

    @Override
    public void deleteOrg(Long id) {
        orgRepository.deleteById(id);
    }

    
}
