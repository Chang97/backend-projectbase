package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.dto.AtchFileItemCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileItemCommandResult;
import com.base.contexts.attachment.application.command.mapper.AtchFileItemCommandMapper;
import com.base.contexts.attachment.application.command.port.in.UpdateAtchFileItemUseCase;
import com.base.contexts.attachment.domain.model.AtchFileItem;
import com.base.contexts.attachment.domain.port.out.AtchFileItemCommandPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateAtchFileItemHandler implements UpdateAtchFileItemUseCase {

    private final AtchFileItemCommandPort atchFileItemCommandPort;
    private final AtchFileItemCommandMapper commandMapper;

    @Override
    public AtchFileItemCommandResult handle(Long atchFileItemId, AtchFileItemCommand command) {
        AtchFileItem existing = atchFileItemCommandPort.findById(atchFileItemId)
                .orElseThrow(() -> new NotFoundException("Attachment file item not found. id=" + atchFileItemId));

        existing.changePath(command.path());
        existing.changeFileName(command.fileName());
        existing.changeFileSize(command.fileSize());
        existing.changeUseYn(command.useYn());

        AtchFileItem saved = atchFileItemCommandPort.save(existing);
        return commandMapper.toResult(saved);
    }
}
