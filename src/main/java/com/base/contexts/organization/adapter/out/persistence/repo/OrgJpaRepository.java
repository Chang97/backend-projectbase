package com.base.contexts.organization.adapter.out.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.contexts.organization.adapter.out.persistence.entity.OrgEntity;

public interface OrgJpaRepository extends JpaRepository<OrgEntity, Long> {

    Optional<OrgEntity> findByOrgCode(String orgCode);

    boolean existsByOrgCode(String orgCode);

    List<OrgEntity> findByUpperOrg_OrgId(Long upperOrgId);

    List<OrgEntity> findByUpperOrg_OrgCode(String upperOrgCode);
}
