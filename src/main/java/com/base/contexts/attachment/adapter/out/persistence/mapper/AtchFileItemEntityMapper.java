package com.base.contexts.attachment.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileEntity;
import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileItemEntity;
import com.base.contexts.attachment.domain.model.AtchFileId;
import com.base.contexts.attachment.domain.model.AtchFileItem;
import com.base.contexts.attachment.domain.model.AtchFileItemId;

@Component
public class AtchFileItemEntityMapper {

    public AtchFileItem toDomain(AtchFileItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return AtchFileItem.restore(
                AtchFileItemId.of(entity.getAtchFileItemId()),
                AtchFileId.of(entity.getAtchFile() != null ? entity.getAtchFile().getAtchFileId() : null),
                entity.getPath(),
                entity.getFileName(),
                entity.getFileSize(),
                entity.getUseYn(),
                entity.getCreatedId(),
                entity.getUpdatedId(),
                entity.getCreatedDt(),
                entity.getUpdatedDt()
        );
    }

    public AtchFileItemEntity toNewEntity(AtchFileItem item, AtchFileEntity parent) {
        return AtchFileItemEntity.builder()
                .atchFile(parent)
                .path(item.getPath())
                .fileName(item.getFileName())
                .fileSize(item.getFileSize())
                .useYn(item.getUseYn())
                .build();
    }

    public void merge(AtchFileItem item, AtchFileItemEntity entity) {
        entity.setPath(item.getPath());
        entity.setFileName(item.getFileName());
        entity.setFileSize(item.getFileSize());
        entity.setUseYn(item.getUseYn());
    }
}
