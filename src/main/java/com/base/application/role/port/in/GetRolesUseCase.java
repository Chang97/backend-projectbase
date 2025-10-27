package com.base.application.role.port.in;

import java.util.List;

import com.base.application.role.usecase.query.condition.RoleSearchCondition;
import com.base.application.role.usecase.result.RoleResult;

public interface GetRolesUseCase {

    List<RoleResult> handle(RoleSearchCondition condition);
}
