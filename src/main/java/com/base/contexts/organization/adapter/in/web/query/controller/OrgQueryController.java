package com.base.contexts.organization.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.organization.adapter.in.web.query.dto.OrgQueryRequest;
import com.base.contexts.organization.adapter.in.web.query.dto.OrgQueryResponse;
import com.base.contexts.organization.adapter.in.web.query.mapper.OrgQueryWebMapper;
import com.base.contexts.organization.application.query.dto.OrgQuery;
import com.base.contexts.organization.application.query.port.in.GetOrgUseCase;
import com.base.contexts.organization.application.query.port.in.GetOrgsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orgs")
@RequiredArgsConstructor
public class OrgQueryController {

    private final GetOrgsUseCase getOrgsUseCase;
    private final GetOrgUseCase getOrgUseCase;
    private final OrgQueryWebMapper queryMapper;

    @GetMapping
    public ResponseEntity<List<OrgQueryResponse>> getOrgs(@ModelAttribute OrgQueryRequest request) {
        OrgQuery query = queryMapper.toQuery(request);
        return ResponseEntity.ok(queryMapper.toResponse(getOrgsUseCase.handle(query)));
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<OrgQueryResponse> getOrg(@PathVariable Long orgId) {
        return ResponseEntity.ok(queryMapper.toResponse(getOrgUseCase.handle(orgId)));
    }
}
