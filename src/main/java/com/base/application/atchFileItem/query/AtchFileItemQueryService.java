package com.base.application.atchFileItem.query;

import java.util.List;

import com.base.api.atchFileItem.dto.AtchFileItemResponse;

public interface AtchFileItemQueryService {
    AtchFileItemResponse getAtchFileItem(Long id);
    List<AtchFileItemResponse> getAtchFileItemList();
}