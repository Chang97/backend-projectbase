package com.base.contexts.organization.application.query.port.in;

import com.base.contexts.organization.application.query.dto.OrgQueryResult;

public interface GetOrgUseCase {

    OrgQueryResult handle(Long orgId);
}
