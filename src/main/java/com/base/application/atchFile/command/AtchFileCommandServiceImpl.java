package com.base.application.atchFile.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.api.atchFile.mapper.AtchFileMapper;
import com.base.domain.atchFile.AtchFile;
import com.base.domain.atchFile.AtchFileRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileCommandServiceImpl implements AtchFileCommandService {

    private final AtchFileRepository atchFileRepository;
    private final AtchFileMapper atchFileMapper;

    @Override
    @Transactional
    public AtchFileResponse createAtchFile(AtchFileRequest request) {
        AtchFile entity = atchFileMapper.toEntity(request);
        return atchFileMapper.toResponse(atchFileRepository.save(entity));
    }

    @Override
    @Transactional
    public AtchFileResponse updateAtchFile(Long id, AtchFileRequest request) {
        AtchFile existing = atchFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFile not found"));
        atchFileMapper.updateEntityFromRequest(request, existing);
        return atchFileMapper.toResponse(atchFileRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteAtchFile(Long id) {
        AtchFile existing = atchFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFile not found"));
        existing.setUseYn(false);
        atchFileRepository.save(existing);
    }
}