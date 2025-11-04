package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.dto.AtchFileItemCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileItemCommandResult;
import com.base.contexts.attachment.application.command.mapper.AtchFileItemCommandMapper;
import com.base.contexts.attachment.application.command.port.in.CreateAtchFileItemUseCase;
import com.base.contexts.attachment.domain.model.AtchFileItem;
import com.base.contexts.attachment.domain.port.out.AtchFileItemRepository;
import com.base.contexts.attachment.domain.port.out.AtchFileRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateAtchFileItemHandler implements CreateAtchFileItemUseCase {

    private final AtchFileRepository atchFileRepository;
    private final AtchFileItemRepository atchFileItemRepository;
    private final AtchFileItemCommandMapper commandMapper;

    @Override
    public AtchFileItemCommandResult handle(AtchFileItemCommand command) {
        Long atchFileId = command.atchFileId();
        atchFileRepository.findById(atchFileId)
                .orElseThrow(() -> new NotFoundException("Attachment file not found. id=" + atchFileId));

        AtchFileItem item = commandMapper.toDomain(command);
        AtchFileItem saved = atchFileItemRepository.save(item);
        return commandMapper.toResult(saved);
    }
}
