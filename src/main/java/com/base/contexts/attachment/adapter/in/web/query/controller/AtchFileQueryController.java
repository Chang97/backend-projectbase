package com.base.contexts.attachment.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileQueryRequest;
import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileQueryResponse;
import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileSummaryResponse;
import com.base.contexts.attachment.adapter.in.web.query.mapper.AtchFileQueryWebMapper;
import com.base.contexts.attachment.application.query.dto.AtchFileQuery;
import com.base.contexts.attachment.application.query.port.in.GetAtchFileUseCase;
import com.base.contexts.attachment.application.query.port.in.GetAtchFilesUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AtchFileQueryController {

    private final GetAtchFilesUseCase getAtchFilesUseCase;
    private final GetAtchFileUseCase getAtchFileUseCase;
    private final AtchFileQueryWebMapper queryMapper;

    @GetMapping
    public ResponseEntity<List<AtchFileSummaryResponse>> getAttachments(
            @ModelAttribute AtchFileQueryRequest request) {
        AtchFileQuery query = queryMapper.toQuery(request);
        return ResponseEntity.ok(queryMapper.toSummaryResponse(getAtchFilesUseCase.handle(query)));
    }

    @GetMapping("/{atchFileId}")
    public ResponseEntity<AtchFileQueryResponse> getAttachment(@PathVariable Long atchFileId) {
        return ResponseEntity.ok(queryMapper.toResponse(getAtchFileUseCase.handle(atchFileId)));
    }
}
