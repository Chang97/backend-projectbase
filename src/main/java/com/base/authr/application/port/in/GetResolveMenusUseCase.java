package com.base.authr.application.port.in;

import java.util.List;

import com.base.authr.application.dto.MenuTreeResult;

public interface GetResolveMenusUseCase {
    List<MenuTreeResult> handle(Long userId);
}
