package com.base.contexts.organization.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.contexts.organization.adapter.out.persistence.entity.OrgEntity;
import com.base.contexts.organization.adapter.out.persistence.mapper.OrgEntityMapper;
import com.base.contexts.organization.adapter.out.persistence.repo.OrgJpaRepository;
import com.base.contexts.organization.domain.model.Org;
import com.base.contexts.organization.domain.model.OrgId;
import com.base.contexts.organization.domain.port.out.OrgRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class OrgRepositoryAdapter implements OrgRepository {

    private final OrgJpaRepository orgJpaRepository;
    private final OrgEntityMapper entityMapper;

    @Override
    public Org save(Org org) {
        OrgEntity upper = resolveUpperOrg(org.getUpperOrgId());

        OrgEntity entity;
        if (org.getOrgId() == null) {
            entity = entityMapper.toNewEntity(org, upper);
        } else {
            entity = orgJpaRepository.findById(org.getOrgId().value())
                    .orElseThrow(() -> new NotFoundException("Org not found"));
            entityMapper.merge(org, entity, upper);
        }
        OrgEntity saved = orgJpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<Org> findById(Long orgId) {
        return orgJpaRepository.findById(orgId)
                .map(entityMapper::toDomain);
    }

    @Override
    public Optional<Org> findByOrgCode(String orgCode) {
        return orgJpaRepository.findByOrgCode(orgCode)
                .map(entityMapper::toDomain);
    }

    @Override
    public boolean existsByOrgCode(String orgCode) {
        return orgJpaRepository.existsByOrgCode(orgCode);
    }

    @Override
    public List<Org> findAll() {
        return orgJpaRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Org> findByUpperOrgId(Long upperOrgId) {
        return orgJpaRepository.findByUpperOrg_OrgId(upperOrgId).stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Org> findByUpperOrgCode(String upperOrgCode) {
        return orgJpaRepository.findByUpperOrg_OrgCode(upperOrgCode).stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long orgId) {
        orgJpaRepository.deleteById(orgId);
    }

    private OrgEntity resolveUpperOrg(OrgId upperOrgId) {
        if (upperOrgId == null) {
            return null;
        }
        return orgJpaRepository.findById(upperOrgId.value())
                .orElseThrow(() -> new NotFoundException("Parent org not found"));
    }
}
