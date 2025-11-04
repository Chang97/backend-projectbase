package com.base.contexts.attachment.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileEntity;
import com.base.contexts.attachment.domain.model.AtchFile;
import com.base.contexts.attachment.domain.model.AtchFileId;

@Component
public class AtchFileEntityMapper {

    public AtchFile toDomain(AtchFileEntity entity) {
        if (entity == null) {
            return null;
        }
        return AtchFile.restore(
                AtchFileId.of(entity.getAtchFileId()),
                entity.getFileGroupCodeId(),
                entity.getUseYn(),
                entity.getCreatedId(),
                entity.getUpdatedId(),
                entity.getCreatedDt(),
                entity.getUpdatedDt()
        );
    }

    public AtchFileEntity toNewEntity(AtchFile atchFile) {
        return AtchFileEntity.builder()
                .fileGroupCodeId(atchFile.getFileGroupCodeId())
                .useYn(atchFile.getUseYn())
                .build();
    }

    public void merge(AtchFile atchFile, AtchFileEntity entity) {
        entity.setFileGroupCodeId(atchFile.getFileGroupCodeId());
        entity.setUseYn(atchFile.getUseYn());
    }
}
