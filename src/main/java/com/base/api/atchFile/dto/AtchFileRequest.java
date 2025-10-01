package com.base.api.atchFile.dto;

public record AtchFileRequest(
    // Entity 필드 그대로 선언
    Long atchFileId,
    Long fileGrpCodeId
) {}