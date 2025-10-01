package com.base.application.atchFile.query;

import java.util.List;

import com.base.api.atchFile.dto.AtchFileResponse;

public interface AtchFileQueryService {
    AtchFileResponse getAtchFile(Long id);
    List<AtchFileResponse> getAtchFileList();
}
