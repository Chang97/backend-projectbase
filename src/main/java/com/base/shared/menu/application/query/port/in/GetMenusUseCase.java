package com.base.shared.menu.application.query.port.in;

import java.util.List;

import com.base.shared.menu.application.query.dto.MenuQuery;
import com.base.shared.menu.application.query.dto.MenuQueryResult;

public interface GetMenusUseCase {

    List<MenuQueryResult> handle(MenuQuery query);
}
