package com.base.application.atchFileItem.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.api.atchFileItem.mapper.AtchFileItemMapper;
import com.base.domain.atchFileItem.AtchFileItem;
import com.base.domain.atchFileItem.AtchFileItemRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileItemCommandServiceImpl implements AtchFileItemCommandService {

    private final AtchFileItemRepository entityVarRepository;
    private final AtchFileItemMapper entityVarMapper;

    @Override
    @Transactional
    public AtchFileItemResponse createAtchFileItem(AtchFileItemRequest request) {
        AtchFileItem entity = entityVarMapper.toEntity(request);
        return entityVarMapper.toResponse(entityVarRepository.save(entity));
    }

    @Override
    @Transactional
    public AtchFileItemResponse updateAtchFileItem(Long id, AtchFileItemRequest request) {
        AtchFileItem existing = entityVarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFileItem not found"));
        entityVarMapper.updateEntityFromRequest(request, existing);
        return entityVarMapper.toResponse(entityVarRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteAtchFileItem(Long id) {
        AtchFileItem existing = entityVarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFileItem not found"));
        existing.setUseYn(false);
        entityVarRepository.save(existing);
    }
}