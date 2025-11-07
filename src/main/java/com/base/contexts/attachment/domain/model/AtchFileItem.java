package com.base.contexts.attachment.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class AtchFileItem {

    private AtchFileItemId atchFileItemId;
    private AtchFileId atchFileId;
    private String path;
    private String fileName;
    private Long fileSize;
    private Boolean useYn;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AtchFileItem(AtchFileItemId atchFileItemId,
            AtchFileId atchFileId,
            String path,
            String fileName,
            Long fileSize,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.atchFileItemId = atchFileItemId;
        this.atchFileId = Objects.requireNonNull(atchFileId, "atchFileId must not be null");
        this.path = path;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AtchFileItem create(AtchFileId atchFileId,
            String path,
            String fileName,
            Long fileSize) {
        return new AtchFileItem(null, atchFileId, path, fileName, fileSize, Boolean.TRUE, null, null, null, null);
    }

    public static AtchFileItem restore(AtchFileItemId atchFileItemId,
            AtchFileId atchFileId,
            String path,
            String fileName,
            Long fileSize,
            Boolean useYn,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new AtchFileItem(atchFileItemId, atchFileId, path, fileName, fileSize, useYn, createdBy, updatedBy, createdAt, updatedAt);
    }

    public AtchFileItemId getAtchFileItemId() {
        return atchFileItemId;
    }

    public AtchFileId getAtchFileId() {
        return atchFileId;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
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

    public void changePath(String path) {
        this.path = path;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }

    public void changeFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void changeUseYn(Boolean useYn) {
        this.useYn = useYn == null ? Boolean.TRUE : useYn;
    }

    public void markPersisted(AtchFileItemId id,
            Long createdBy,
            Long updatedBy,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.atchFileItemId = id;
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
