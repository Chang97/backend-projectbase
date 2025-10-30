package com.base.contexts.authr.application.port.in;

import com.base.contexts.authr.application.dto.UserMenuAccessResult;

public interface GetResolveMenusUseCase {
    UserMenuAccessResult handle(Long userId);
}
