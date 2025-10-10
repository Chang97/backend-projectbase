package com.base.application.atchFileItem.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.api.atchFileItem.mapper.AtchFileItemMapper;
import com.base.domain.atchFile.AtchFile;
import com.base.domain.atchFileItem.AtchFileItem;
import com.base.domain.atchFileItem.AtchFileItemRepository;
import com.base.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileItemCommandServiceImpl implements AtchFileItemCommandService {

    private final AtchFileItemRepository atchFileItemRepository;
    private final AtchFileItemMapper atchFileItemMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public AtchFileItemResponse createAtchFileItem(AtchFileItemRequest request) {
        AtchFileItem entity = atchFileItemMapper.toEntity(request);
        applyAtchFile(entity, request.atchFileId());
        return atchFileItemMapper.toResponse(atchFileItemRepository.save(entity));
    }

    @Override
    @Transactional
    public AtchFileItemResponse updateAtchFileItem(Long id, AtchFileItemRequest request) {
        AtchFileItem existing = atchFileItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFileItem not found"));
        atchFileItemMapper.updateEntityFromRequest(request, existing);
        applyAtchFile(existing, request.atchFileId());
        return atchFileItemMapper.toResponse(atchFileItemRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteAtchFileItem(Long id) {
        AtchFileItem existing = atchFileItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFileItem not found"));
        existing.setUseYn(false);
        atchFileItemRepository.save(existing);
    }

    private void applyAtchFile(AtchFileItem entity, Long atchFileId) {
        if (atchFileId != null) {
            entity.setAtchFile(entityManager.getReference(AtchFile.class, atchFileId));
        } else {
            entity.setAtchFile(null);
        }
    }
}
