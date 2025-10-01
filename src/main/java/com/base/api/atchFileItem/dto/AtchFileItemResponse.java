package com.base.api.atchFileItem.dto;

public record AtchFileItemResponse(
        Long atchFileItemId,
        Long atchFileId,
        String path,
        String fileName
) {}