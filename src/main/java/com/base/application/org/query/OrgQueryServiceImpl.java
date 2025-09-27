package com.base.application.org.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.org.Org;
import com.base.domain.org.OrgRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgQueryServiceImpl implements OrgQueryService {

    private final OrgRepository orgRepository;

    @Override
    public List<Org> getOrgs() {
        return orgRepository.findAll();
    }

    @Override
    public Org getOrg(Long id) {
        return orgRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Org not found"));
    }
}
