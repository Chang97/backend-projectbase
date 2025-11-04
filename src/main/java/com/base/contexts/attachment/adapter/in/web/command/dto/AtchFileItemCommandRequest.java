package com.base.contexts.attachment.adapter.in.web.command.dto;

public record AtchFileItemCommandRequest(
        String path,
        String fileName,
        Long fileSize,
        Boolean useYn) {
}
