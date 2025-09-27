package com.base.application.org.query;

import java.util.List;

import com.base.api.org.dto.OrgResponse;

public interface OrgQueryService {

    OrgResponse getOrg(Long id);
    List<OrgResponse> getOrgs();
    List<OrgResponse> getOrgsByUpperId(Long upperOrgId);
    List<OrgResponse> getOrgsByUpperOrg(String upperOrg);

}
