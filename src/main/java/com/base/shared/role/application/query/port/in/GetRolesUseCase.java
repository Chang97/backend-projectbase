package com.base.shared.role.application.query.port.in;

import java.util.List;

import com.base.shared.role.application.query.dto.RoleQuery;
import com.base.shared.role.application.query.dto.RoleQueryResult;

public interface GetRolesUseCase {

    List<RoleQueryResult> handle(RoleQuery query);
}
