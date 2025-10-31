package com.base.contexts.code.adapter.in.web.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.contexts.code.adapter.in.web.query.dto.CodeQueryRequest;
import com.base.contexts.code.adapter.in.web.query.dto.CodeQueryResponse;
import com.base.contexts.code.adapter.in.web.query.mapper.CodeQueryWebMapper;
import com.base.contexts.code.application.query.dto.CodeQuery;
import com.base.contexts.code.application.query.dto.CodeQueryResult;
import com.base.contexts.code.application.query.port.in.GetCodeUseCase;
import com.base.contexts.code.application.query.port.in.GetCodesByUpperCodeUseCase;
import com.base.contexts.code.application.query.port.in.GetCodesByUpperIdUseCase;
import com.base.contexts.code.application.query.port.in.GetCodesUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeQueryController {
    
    private final GetCodeUseCase getCodeUseCase;
    private final GetCodesUseCase getCodesUseCase;
    private final GetCodesByUpperIdUseCase getCodesByUpperIdUseCase;
    private final GetCodesByUpperCodeUseCase getCodesByUpperCodeUseCase;
    private final CodeQueryWebMapper codeQueryWebMapper;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_READ')")
    public ResponseEntity<CodeQueryResponse> getCode(@PathVariable Long id) {
        CodeQueryResult result = getCodeUseCase.handle(id);
        return ResponseEntity.ok(codeQueryWebMapper.toResponse(result));
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('CODE_LIST')")
    public ResponseEntity<List<CodeQueryResponse>> getCodes(
            @ModelAttribute CodeQueryRequest request
    ) {
        CodeQuery query = codeQueryWebMapper.toQuery(request);
        return ResponseEntity.ok(codeQueryWebMapper.toResponse(getCodesUseCase.handle(query)));
    }

    @GetMapping("/group/{upperCodeId}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_ID')")
    public ResponseEntity<List<CodeQueryResponse>> getCodesByUpperId(@PathVariable Long upperCodeId) {
        return ResponseEntity.ok(codeQueryWebMapper.toResponse(getCodesByUpperIdUseCase.handle(upperCodeId)));
    }

    @GetMapping("/group/code/{upperCode}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_CODE')")
    public ResponseEntity<List<CodeQueryResponse>> getCodesByUpperCode(@PathVariable String upperCode) {
        return ResponseEntity.ok(codeQueryWebMapper.toResponse(getCodesByUpperCodeUseCase.handle(upperCode)));
    }

}
