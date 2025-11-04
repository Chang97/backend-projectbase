package com.base.contexts.attachment.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileItemQueryResponse;
import com.base.contexts.attachment.adapter.in.web.query.mapper.AtchFileQueryWebMapper;
import com.base.contexts.attachment.application.query.port.in.GetAtchFileItemsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attachments/{atchFileId}/items")
@RequiredArgsConstructor
public class AtchFileItemQueryController {

    private final GetAtchFileItemsUseCase getAtchFileItemsUseCase;
    private final AtchFileQueryWebMapper queryMapper;

    @GetMapping
    public ResponseEntity<List<AtchFileItemQueryResponse>> getItems(@PathVariable Long atchFileId) {
        return ResponseEntity.ok(queryMapper.toItemResponse(getAtchFileItemsUseCase.handle(atchFileId)));
    }
}
