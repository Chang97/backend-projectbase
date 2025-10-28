package com.base.identity.user.application.query.port.in;

import com.base.identity.user.application.query.dto.UserQueryResult;

public interface GetUserUseCase {

    UserQueryResult handle(Long userId);
}
