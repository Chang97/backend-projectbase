package com.base.contexts.attachment.application.command.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;
import com.base.contexts.attachment.domain.model.AtchFile;

@Component
public class AtchFileCommandMapper {

    public AtchFile toDomain(AtchFileCommand command) {
        AtchFile atchFile = AtchFile.create(command.fileGroupCodeId());
        atchFile.changeUseYn(command.useYn());
        return atchFile;
    }

    public AtchFileCommandResult toResult(AtchFile atchFile) {
        Long id = atchFile.getAtchFileId() != null ? atchFile.getAtchFileId().value() : null;
        return new AtchFileCommandResult(
                id,
                atchFile.getFileGroupCodeId(),
                atchFile.getUseYn(),
                atchFile.getCreatedBy(),
                atchFile.getUpdatedBy(),
                atchFile.getCreatedAt(),
                atchFile.getUpdatedAt()
        );
    }
}
