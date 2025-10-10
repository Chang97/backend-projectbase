package com.base.application.atchFile.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.api.atchFile.mapper.AtchFileMapper;
import com.base.domain.atchFile.AtchFile;
import com.base.domain.atchFile.AtchFileRepository;
import com.base.domain.code.Code;
import com.base.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileCommandServiceImpl implements AtchFileCommandService {

    private final AtchFileRepository atchFileRepository;
    private final AtchFileMapper atchFileMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public AtchFileResponse createAtchFile(AtchFileRequest request) {
        AtchFile entity = atchFileMapper.toEntity(request);
        applyFileGrpCode(entity, request.fileGrpCodeId());
        return atchFileMapper.toResponse(atchFileRepository.save(entity));
    }

    @Override
    @Transactional
    public AtchFileResponse updateAtchFile(Long id, AtchFileRequest request) {
        AtchFile existing = atchFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFile not found"));
        atchFileMapper.updateEntityFromRequest(request, existing);
        applyFileGrpCode(existing, request.fileGrpCodeId());
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

    private void applyFileGrpCode(AtchFile entity, Long fileGrpCodeId) {
        if (fileGrpCodeId != null) {
            entity.setFileGrpCode(entityManager.getReference(Code.class, fileGrpCodeId));
        } else {
            entity.setFileGrpCode(null);
        }
    }
}
