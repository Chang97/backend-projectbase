package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;
import com.base.contexts.attachment.application.command.mapper.AtchFileCommandMapper;
import com.base.contexts.attachment.application.command.port.in.UpdateAtchFileUseCase;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.port.out.AtchFileCommandPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateAtchFileHandler implements UpdateAtchFileUseCase {

    private final AtchFileCommandPort atchFileCommandPort;
    private final AtchFileCommandMapper commandMapper;

    @Override
    public AtchFileCommandResult handle(Long atchFileId, AtchFileCommand command) {
        AtchFile existing = atchFileCommandPort.findById(atchFileId)
                .orElseThrow(() -> new NotFoundException("Attachment file not found. id=" + atchFileId));
        existing.changeFileGroupCodeId(command.fileGroupCodeId());
        existing.changeUseYn(command.useYn());

        AtchFile saved = atchFileCommandPort.save(existing);
        return commandMapper.toResult(saved);
    }
}
