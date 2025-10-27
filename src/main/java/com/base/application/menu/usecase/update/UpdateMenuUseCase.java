package com.base.application.menu.usecase.update;

import com.base.application.menu.usecase.command.UpdateMenuCommand;
import com.base.application.menu.usecase.result.MenuResult;

public interface UpdateMenuUseCase {

    MenuResult handle(Long menuId, UpdateMenuCommand command);
}
