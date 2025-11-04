package com.base.contexts.organization.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.base.contexts.organization.adapter.in.web.query.dto.OrgQueryRequest;
import com.base.contexts.organization.adapter.in.web.query.dto.OrgQueryResponse;
import com.base.contexts.organization.application.query.dto.OrgQuery;
import com.base.contexts.organization.application.query.dto.OrgQueryResult;

@Component
public class OrgQueryWebMapper {

    public OrgQuery toQuery(OrgQueryRequest request) {
        if (request == null) {
            return new OrgQuery(null, null, null, null, null);
        }
        return new OrgQuery(
                request.upperOrgId(),
                normalize(request.upperOrgCode()),
                normalize(request.orgCode()),
                normalize(request.orgName()),
                request.useYn()
        );
    }

    public OrgQueryResponse toResponse(OrgQueryResult result) {
        return new OrgQueryResponse(
                result.orgId(),
                result.upperOrgId(),
                result.orgCode(),
                result.orgName(),
                result.srt(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    public List<OrgQueryResponse> toResponse(List<OrgQueryResult> results) {
        return results.stream()
                .map(this::toResponse)
                .toList();
    }

    private String normalize(String text) {
        return StringUtils.hasText(text) ? text.trim() : null;
    }
}
