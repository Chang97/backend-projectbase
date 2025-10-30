package com.base.contexts.authr.role.application.query.port.in;

import java.util.List;

import com.base.contexts.authr.role.application.query.dto.RoleQuery;
import com.base.contexts.authr.role.application.query.dto.RoleQueryResult;

public interface GetRolesUseCase {

    List<RoleQueryResult> handle(RoleQuery query);
}
