package com.base.application.atchFile.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.api.atchFile.mapper.AtchFileMapper;
import com.base.domain.atchFile.AtchFileRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileQueryServiceImpl implements AtchFileQueryService {

    private final AtchFileRepository entityVarRepository;
    private final AtchFileMapper entityVarMapper;

    @Override
    @Transactional(readOnly = true)
    public AtchFileResponse getAtchFile(Long id) {
        return entityVarMapper.toResponse(entityVarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFile not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtchFileResponse> getAtchFileList() {
        return entityVarRepository.findAll().stream()
                .map(entityVarMapper::toResponse)
                .toList();
    }
}