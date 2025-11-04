package com.base.contexts.attachment.application.command.dto;

public record AtchFileItemCommand(Long atchFileId,
        String path,
        String fileName,
        Long fileSize,
        Boolean useYn) {
}
