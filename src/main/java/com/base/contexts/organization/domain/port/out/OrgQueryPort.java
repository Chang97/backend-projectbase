package com.base.contexts.organization.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.organization.domain.model.Org;

public interface OrgQueryPort {

    Optional<Org> findById(Long orgId);

    Optional<Org> findByOrgCode(String orgCode);

    List<Org> findAll();

    List<Org> findByUpperOrgId(Long upperOrgId);

    List<Org> findByUpperOrgCode(String upperOrgCode);
}
