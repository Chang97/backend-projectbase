package com.base.contexts.organization.application.command.port.in;

import com.base.contexts.organization.application.command.dto.OrgCommand;
import com.base.contexts.organization.application.command.dto.OrgCommandResult;

public interface UpdateOrgUseCase {

    OrgCommandResult handle(Long orgId, OrgCommand command);
}
