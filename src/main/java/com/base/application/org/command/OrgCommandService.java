package com.base.application.org.command;

import com.base.api.org.dto.OrgRequest;
import com.base.api.org.dto.OrgResponse;

public interface OrgCommandService {

    OrgResponse createOrg(OrgRequest request);
    OrgResponse updateOrg(Long id, OrgRequest request);
    void deleteOrg(Long id);
    
}
