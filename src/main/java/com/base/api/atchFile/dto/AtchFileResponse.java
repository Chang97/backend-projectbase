package com.base.api.atchFile.dto;

public record AtchFileResponse(
        Long fileId,
        Long fileGrpCodeId,
        String fileGrpCodeName
) {}