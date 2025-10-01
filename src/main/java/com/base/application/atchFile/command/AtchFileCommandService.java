package com.base.application.atchFile.command;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;

public interface AtchFileCommandService {
    AtchFileResponse createAtchFile(AtchFileRequest request);
    AtchFileResponse updateAtchFile(Long id, AtchFileRequest request);
    void deleteAtchFile(Long id);
}