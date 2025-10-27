package com.base.application.menu.usecase.query.detail;

import com.base.application.menu.usecase.result.MenuResult;

public interface GetMenuUseCase {

    MenuResult handle(Long menuId);
}
