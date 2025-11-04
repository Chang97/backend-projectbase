package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.port.in.DeleteAtchFileUseCase;
import com.base.contexts.attachment.domain.port.out.AtchFileItemRepository;
import com.base.contexts.attachment.domain.port.out.AtchFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteAtchFileHandler implements DeleteAtchFileUseCase {

    private final AtchFileRepository atchFileRepository;
    private final AtchFileItemRepository atchFileItemRepository;

    @Override
    public void handle(Long atchFileId) {
        atchFileItemRepository.deleteByAtchFileId(atchFileId);
        atchFileRepository.deleteById(atchFileId);
    }
}
