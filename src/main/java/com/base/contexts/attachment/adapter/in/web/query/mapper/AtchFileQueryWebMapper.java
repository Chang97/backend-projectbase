package com.base.contexts.attachment.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileItemQueryResponse;
import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileQueryRequest;
import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileQueryResponse;
import com.base.contexts.attachment.adapter.in.web.query.dto.AtchFileSummaryResponse;
import com.base.contexts.attachment.application.query.dto.AtchFileItemResult;
import com.base.contexts.attachment.application.query.dto.AtchFileQuery;
import com.base.contexts.attachment.application.query.dto.AtchFileQueryResult;
import com.base.contexts.attachment.application.query.dto.AtchFileSummaryResult;

@Component
public class AtchFileQueryWebMapper {

    public AtchFileQuery toQuery(AtchFileQueryRequest request) {
        if (request == null) {
            return new AtchFileQuery(null, null);
        }
        return new AtchFileQuery(request.fileGroupCodeId(), request.useYn());
    }

    public AtchFileSummaryResponse toResponse(AtchFileSummaryResult result) {
        return new AtchFileSummaryResponse(
                result.atchFileId(),
                result.fileGroupCodeId(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    public List<AtchFileSummaryResponse> toSummaryResponse(List<AtchFileSummaryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

    public AtchFileQueryResponse toResponse(AtchFileQueryResult result) {
        List<AtchFileItemQueryResponse> itemResponses = result.items().stream()
                .map(this::toResponse)
                .toList();
        return new AtchFileQueryResponse(
                result.atchFileId(),
                result.fileGroupCodeId(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt(),
                itemResponses
        );
    }

    public AtchFileItemQueryResponse toResponse(AtchFileItemResult result) {
        return new AtchFileItemQueryResponse(
                result.atchFileItemId(),
                result.atchFileId(),
                result.path(),
                result.fileName(),
                result.fileSize(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    public List<AtchFileItemQueryResponse> toItemResponse(List<AtchFileItemResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }
}
