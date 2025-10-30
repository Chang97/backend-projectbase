package com.base.contexts.authr.menu.application.command.port.in;

import com.base.contexts.authr.menu.application.command.dto.MenuCommand;
import com.base.contexts.authr.menu.application.command.dto.MenuCommandResult;

public interface CreateMenuUseCase {

    MenuCommandResult handle(MenuCommand command);
}
