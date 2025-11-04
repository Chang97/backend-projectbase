package com.base.contexts.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.command.port.in.DeleteAtchFileItemUseCase;
import com.base.contexts.attachment.domain.port.out.AtchFileItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteAtchFileItemHandler implements DeleteAtchFileItemUseCase {

    private final AtchFileItemRepository atchFileItemRepository;

    @Override
    public void handle(Long atchFileItemId) {
        atchFileItemRepository.deleteById(atchFileItemId);
    }
}
