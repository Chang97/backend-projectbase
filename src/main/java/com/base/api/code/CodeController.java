package com.base.api.code;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.application.code.command.CodeCommandService;
import com.base.application.code.query.CodeQueryService;
import com.base.application.code.query.CodeSearchCondition;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {
    
    private final CodeCommandService codeCommandService;
    private final CodeQueryService codeQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('CODE_CREATE')")
    public ResponseEntity<CodeResponse> createCode(@Valid @RequestBody CodeRequest request) {
        return ResponseEntity.ok(codeCommandService.createCode(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_UPDATE')")
    public ResponseEntity<CodeResponse> updateCode(@PathVariable Long id, @Valid @RequestBody CodeRequest request) {
        return ResponseEntity.ok(codeCommandService.updateCode(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_DELETE')")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        codeCommandService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_READ')")
    public ResponseEntity<CodeResponse> getCode(@PathVariable Long id) {
        return ResponseEntity.ok(codeQueryService.getCode(id));
    }

    /**
     * 코드 목록을 조회한다. 화면에서 전달되는 검색 조건은 {@link CodeSearchCondition}에 수집되며
     * 조건이 비어 있으면 전체 목록을 반환한다.
    */
    @GetMapping
    @PreAuthorize("hasAuthority('CODE_LIST')")
    public ResponseEntity<List<CodeResponse>> getCodes(
            @ModelAttribute CodeSearchCondition condition
    ) {
        return ResponseEntity.ok(codeQueryService.getCodes(condition));
    }

    @GetMapping("/group/{upperCodeId}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_ID')")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperId(@PathVariable Long upperCodeId) {
        return ResponseEntity.ok(codeQueryService.getCodesByUpperId(upperCodeId));
    }

    @GetMapping("/group/code/{upperCode}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_CODE')")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperCode(@PathVariable String upperCode) {
        return ResponseEntity.ok(codeQueryService.getCodesByUpperCode(upperCode));
    }

}
