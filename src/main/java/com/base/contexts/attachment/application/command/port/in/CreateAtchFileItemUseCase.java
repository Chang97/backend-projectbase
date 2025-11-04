package com.base.contexts.attachment.application.command.port.in;

import com.base.contexts.attachment.application.command.dto.AtchFileItemCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileItemCommandResult;

public interface CreateAtchFileItemUseCase {

    AtchFileItemCommandResult handle(AtchFileItemCommand command);
}
