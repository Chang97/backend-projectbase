package com.base.api.atchFileItem.dto;

public record AtchFileItemRequest(
        Long atchFileItemId,
        Long atchFileId,
        String path,
        String fileName,
        Long fileSize
) {}