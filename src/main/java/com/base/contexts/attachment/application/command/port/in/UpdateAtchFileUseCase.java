package com.base.contexts.attachment.application.command.port.in;

import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;

public interface UpdateAtchFileUseCase {

    AtchFileCommandResult handle(Long atchFileId, AtchFileCommand command);
}
