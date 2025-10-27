package com.base.identity.user.application.port.in.query;

import com.base.application.user.usecase.result.UserResult;

public interface GetUserUseCase {

    UserResult handle(Long userId);
}
