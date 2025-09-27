package com.base.application.org.query;

import java.util.List;

import com.base.domain.org.Org;

public interface OrgQueryService {

    List<Org> getOrgs();
    Org getOrg(Long id);

}
