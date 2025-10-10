package com.base.api.atchFile.dto;

public record AtchFileResponse(
        Long atchFileId,
        Long fileGrpCodeId,
        String fileGrpCodeName
) {}