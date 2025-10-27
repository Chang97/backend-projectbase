package com.base.identity.user.application.port.in.query;

import java.util.List;

import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.application.user.usecase.result.UserResult;

public interface GetUsersUseCase {

    List<UserResult> handle(UserSearchCondition condition);
}
