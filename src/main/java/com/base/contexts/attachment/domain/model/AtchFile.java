package com.base.contexts.attachment.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class AtchFile {

    private AtchFileId atchFileId;
    private Long fileGroupCodeId;
    private Boolean useYn;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AtchFile(AtchFileId atchFileId,
            Long fileGroupCodeId,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.atchFileId = atchFileId;
        this.fileGroupCodeId = Objects.requireNonNull(fileGroupCodeId, "fileGroupCodeId must not be null");
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AtchFile create(Long fileGroupCodeId) {
        return new AtchFile(null, fileGroupCodeId, Boolean.TRUE, null, null, null, null);
    }

    public static AtchFile restore(AtchFileId atchFileId,
            Long fileGroupCodeId,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new AtchFile(atchFileId, fileGroupCodeId, useYn, createdBy, updatedBy, createdAt, updatedAt);
    }

    public AtchFileId getAtchFileId() {
        return atchFileId;
    }

    public Long getFileGroupCodeId() {
        return fileGroupCodeId;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changeFileGroupCodeId(Long fileGroupCodeId) {
        this.fileGroupCodeId = Objects.requireNonNull(fileGroupCodeId, "fileGroupCodeId must not be null");
    }

    public void changeUseYn(Boolean useYn) {
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
    }

    public void markPersisted(AtchFileId id,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.atchFileId = id;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateAudit(Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
