package com.base.application.menu.usecase.query.list;

import java.util.List;

import com.base.application.menu.usecase.result.MenuResult;

public interface GetMenusByUpperMenuUseCase {

    List<MenuResult> handle(String upperMenuCode);
}
