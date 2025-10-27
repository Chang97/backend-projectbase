package com.base.application.user.port.in;

import com.base.application.user.usecase.result.UserResult;

public interface GetUserUseCase {

    UserResult handle(Long userId);
}
