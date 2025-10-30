package com.base.contexts.authr.menu.application.query.port.in;

import java.util.List;

import com.base.contexts.authr.menu.application.query.dto.MenuQuery;
import com.base.contexts.authr.menu.application.query.dto.MenuQueryResult;

public interface GetMenusUseCase {

    List<MenuQueryResult> handle(MenuQuery query);
}
