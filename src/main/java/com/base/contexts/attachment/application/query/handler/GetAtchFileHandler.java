package com.base.contexts.attachment.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.query.dto.AtchFileQueryResult;
import com.base.contexts.attachment.application.query.mapper.AtchFileQueryMapper;
import com.base.contexts.attachment.application.query.port.in.GetAtchFileUseCase;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.port.out.AtchFileItemQueryPort;
import com.base.contexts.attachment.domain.port.out.AtchFileQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAtchFileHandler implements GetAtchFileUseCase {

    private final AtchFileQueryPort atchFileQueryPort;
    private final AtchFileItemQueryPort atchFileItemQueryPort;
    private final AtchFileQueryMapper queryMapper;

    @Override
    public AtchFileQueryResult handle(Long atchFileId) {
        AtchFile atchFile = atchFileQueryPort.findById(atchFileId)
                .orElseThrow(() -> new NotFoundException("Attachment file not found. id=" + atchFileId));
        return queryMapper.toResult(atchFile, atchFileItemQueryPort.findByAtchFileId(atchFileId));
    }
}
