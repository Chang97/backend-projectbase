package com.base.contexts.attachment.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.query.dto.AtchFileItemResult;
import com.base.contexts.attachment.application.query.mapper.AtchFileQueryMapper;
import com.base.contexts.attachment.application.query.port.in.GetAtchFileItemsUseCase;
import com.base.contexts.attachment.domain.port.out.AtchFileItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAtchFileItemsHandler implements GetAtchFileItemsUseCase {

    private final AtchFileItemRepository atchFileItemRepository;
    private final AtchFileQueryMapper queryMapper;

    @Override
    public List<AtchFileItemResult> handle(Long atchFileId) {
        return atchFileItemRepository.findByAtchFileId(atchFileId).stream()
                .map(queryMapper::toItemResult)
                .toList();
    }
}
