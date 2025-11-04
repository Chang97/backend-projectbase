package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.dto.AtchFileCommand;
import com.base.contexts.attachment.application.command.dto.AtchFileCommandResult;
import com.base.contexts.attachment.application.command.mapper.AtchFileCommandMapper;
import com.base.contexts.attachment.application.command.port.in.CreateAtchFileUseCase;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.port.out.AtchFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateAtchFileHandler implements CreateAtchFileUseCase {

    private final AtchFileRepository atchFileRepository;
    private final AtchFileCommandMapper commandMapper;

    @Override
    public AtchFileCommandResult handle(AtchFileCommand command) {
        AtchFile atchFile = commandMapper.toDomain(command);
        AtchFile saved = atchFileRepository.save(atchFile);
        return commandMapper.toResult(saved);
    }
}
