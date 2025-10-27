package com.base.application.menu.usecase.create;

import com.base.application.menu.usecase.command.CreateMenuCommand;
import com.base.application.menu.usecase.result.MenuResult;

public interface CreateMenuUseCase {

    MenuResult handle(CreateMenuCommand command);
}
