package com.base.contexts.attachment.application.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.application.query.dto.AtchFileItemResult;
import com.base.contexts.attachment.application.query.dto.AtchFileQueryResult;
import com.base.contexts.attachment.application.query.dto.AtchFileSummaryResult;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.model.AtchFileItem;

@Component
public class AtchFileQueryMapper {

    public AtchFileSummaryResult toSummary(AtchFile atchFile) {
        return new AtchFileSummaryResult(
                atchFile.getAtchFileId() != null ? atchFile.getAtchFileId().value() : null,
                atchFile.getFileGroupCodeId(),
                atchFile.getUseYn(),
                atchFile.getCreatedBy(),
                atchFile.getUpdatedBy(),
                atchFile.getCreatedAt(),
                atchFile.getUpdatedAt()
        );
    }

    public AtchFileQueryResult toResult(AtchFile atchFile, List<AtchFileItem> items) {
        List<AtchFileItemResult> itemResults = items.stream()
                .map(this::toItemResult)
                .toList();
        return new AtchFileQueryResult(
                atchFile.getAtchFileId() != null ? atchFile.getAtchFileId().value() : null,
                atchFile.getFileGroupCodeId(),
                atchFile.getUseYn(),
                atchFile.getCreatedBy(),
                atchFile.getUpdatedBy(),
                atchFile.getCreatedAt(),
                atchFile.getUpdatedAt(),
                itemResults
        );
    }

    public AtchFileItemResult toItemResult(AtchFileItem item) {
        return new AtchFileItemResult(
                item.getAtchFileItemId() != null ? item.getAtchFileItemId().value() : null,
                item.getAtchFileId() != null ? item.getAtchFileId().value() : null,
                item.getPath(),
                item.getFileName(),
                item.getFileSize(),
                item.getUseYn(),
                item.getCreatedBy(),
                item.getUpdatedBy(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
