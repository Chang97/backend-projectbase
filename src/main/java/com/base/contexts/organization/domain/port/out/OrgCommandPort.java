package com.base.contexts.organization.domain.port.out;

import java.util.Optional;

import com.base.contexts.organization.domain.model.Org;

public interface OrgCommandPort {

    Org save(Org org);

    Optional<Org> findById(Long orgId);

    Optional<Org> findByOrgCode(String orgCode);

    boolean existsByOrgCode(String orgCode);

    void deleteById(Long orgId);
}
