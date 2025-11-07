package com.base.contexts.attachment.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileEntity;
import com.base.contexts.attachment.adapter.out.persistence.mapper.AtchFileEntityMapper;
import com.base.contexts.attachment.adapter.out.persistence.repo.AtchFileJpaRepository;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.port.out.AtchFileCommandPort;
import com.base.contexts.attachment.domain.port.out.AtchFileQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AtchFileRepositoryAdapter implements AtchFileCommandPort, AtchFileQueryPort {

    private final AtchFileJpaRepository atchFileJpaRepository;
    private final AtchFileEntityMapper entityMapper;

    @Override
    public AtchFile save(AtchFile atchFile) {
        AtchFileEntity entity;
        if (atchFile.getAtchFileId() == null) {
            entity = entityMapper.toNewEntity(atchFile);
        } else {
            entity = atchFileJpaRepository.findById(atchFile.getAtchFileId().value())
                    .orElseThrow(() -> new NotFoundException("Attachment file not found"));
            entityMapper.merge(atchFile, entity);
        }
        AtchFileEntity saved = atchFileJpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<AtchFile> findById(Long atchFileId) {
        return atchFileJpaRepository.findById(atchFileId)
                .map(entityMapper::toDomain);
    }

    @Override
    public List<AtchFile> findAll() {
        return atchFileJpaRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long atchFileId) {
        atchFileJpaRepository.deleteById(atchFileId);
    }
}
