package com.base.contexts.attachment.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.adapter.in.web.command.dto.AtchFileCommandRequest;
import com.base.contexts.attachment.adapter.in.web.command.dto.AtchFileCommandResponse;
import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;

@Component
public class AtchFileCommandWebMapper {

    public AtchFileCommand toCommand(AtchFileCommandRequest request) {
        return new AtchFileCommand(request.fileGroupCodeId(), request.useYn());
    }

    public AtchFileCommandResponse toResponse(AtchFileCommandResult result) {
        return new AtchFileCommandResponse(
                result.atchFileId(),
                result.fileGroupCodeId(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
