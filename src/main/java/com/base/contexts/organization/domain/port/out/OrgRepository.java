package com.base.contexts.organization.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.organization.domain.model.Org;

public interface OrgRepository {

    Org save(Org org);

    Optional<Org> findById(Long orgId);

    Optional<Org> findByOrgCode(String orgCode);

    boolean existsByOrgCode(String orgCode);

    List<Org> findAll();

    List<Org> findByUpperOrgId(Long upperOrgId);

    List<Org> findByUpperOrgCode(String upperOrgCode);

    void deleteById(Long orgId);
}
