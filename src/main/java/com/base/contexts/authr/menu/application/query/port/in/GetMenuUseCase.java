package com.base.contexts.authr.menu.application.query.port.in;

import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;

public interface GetMenuUseCase {

    MenuQueryResult handle(Long menuId);
}
