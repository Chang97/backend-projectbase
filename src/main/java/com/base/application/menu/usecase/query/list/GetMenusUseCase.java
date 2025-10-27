package com.base.application.menu.usecase.query.list;

import java.util.List;

import com.base.application.menu.usecase.query.condition.MenuSearchCondition;
import com.base.application.menu.usecase.result.MenuResult;

public interface GetMenusUseCase {

    List<MenuResult> handle(MenuSearchCondition condition);
}
