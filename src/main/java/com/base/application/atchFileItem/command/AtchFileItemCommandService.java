package com.base.application.atchFileItem.command;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;

public interface AtchFileItemCommandService {
    AtchFileItemResponse createAtchFileItem(AtchFileItemRequest request);
    AtchFileItemResponse updateAtchFileItem(Long id, AtchFileItemRequest request);
    void deleteAtchFileItem(Long id);
}
