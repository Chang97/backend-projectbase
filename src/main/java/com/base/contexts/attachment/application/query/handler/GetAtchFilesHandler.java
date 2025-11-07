package com.base.contexts.attachment.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.attachment.application.query.dto.AtchFileQuery;
import com.base.contexts.attachment.application.query.dto.AtchFileSummaryResult;
import com.base.contexts.attachment.application.query.mapper.AtchFileQueryMapper;
import com.base.contexts.attachment.application.query.port.in.GetAtchFilesUseCase;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.port.out.AtchFileQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAtchFilesHandler implements GetAtchFilesUseCase {

    private final AtchFileQueryPort atchFileQueryPort;
    private final AtchFileQueryMapper queryMapper;

    @Override
    public List<AtchFileSummaryResult> handle(AtchFileQuery query) {
        List<AtchFile> atchFiles = atchFileQueryPort.findAll();
        return atchFiles.stream()
                .filter(file -> filterByGroupCode(file, query))
                .filter(file -> filterByUseYn(file, query))
                .map(queryMapper::toSummary)
                .toList();
    }

    private boolean filterByGroupCode(AtchFile file, AtchFileQuery query) {
        if (query == null || query.fileGroupCodeId() == null) {
            return true;
        }
        return query.fileGroupCodeId().equals(file.getFileGroupCodeId());
    }

    private boolean filterByUseYn(AtchFile file, AtchFileQuery query) {
        if (query == null || query.useYn() == null) {
            return true;
        }
        return query.useYn().equals(Boolean.TRUE.equals(file.getUseYn()));
    }
}
