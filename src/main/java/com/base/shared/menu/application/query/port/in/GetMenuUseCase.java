package com.base.shared.menu.application.query.port.in;

import com.base.shared.menu.application.query.dto.MenuQueryResult;

public interface GetMenuUseCase {

    MenuQueryResult handle(Long menuId);
}
