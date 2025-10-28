package com.base.shared.menu.application.command.port.in;

import com.base.shared.menu.application.command.dto.MenuCommand;
import com.base.shared.menu.application.command.dto.MenuCommandResult;

public interface CreateMenuUseCase {

    MenuCommandResult handle(MenuCommand command);
}
