package com.base.authr.application.port.in;

import com.base.authr.application.dto.UserMenuAccessResult;

public interface GetResolveMenusUseCase {
    UserMenuAccessResult handle(Long userId);
}
