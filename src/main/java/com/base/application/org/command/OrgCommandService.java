package com.base.application.org.command;

import com.base.domain.org.Org;

public interface OrgCommandService {

    Org createOrg(Org code);
    Org updateOrg(Long id, Org code);
    void deleteOrg(Long id);
    
}
