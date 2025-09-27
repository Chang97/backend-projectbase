package com.base.api.code;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.application.code.command.CodeCommandService;
import com.base.application.code.query.CodeQueryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {
    
    private final CodeCommandService codeCommandService;
    private final CodeQueryService codeQueryService;

    @PostMapping
    public ResponseEntity<CodeResponse> createCode(@RequestBody CodeRequest request) {
        return ResponseEntity.ok(codeCommandService.createCode(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodeResponse> updateCode(@PathVariable Long id, @RequestBody CodeRequest request) {
        return ResponseEntity.ok(codeCommandService.updateCode(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        codeCommandService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodeResponse> getCode(@PathVariable Long id) {
        return ResponseEntity.ok(codeQueryService.getCode(id));
    }

    @GetMapping
    public ResponseEntity<List<CodeResponse>> getCodes() {
        return ResponseEntity.ok(codeQueryService.getCodes());
    }

    @GetMapping("/group/{upperCodeId}")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperId(@PathVariable Long upperCodeId) {
        return ResponseEntity.ok(codeQueryService.getCodesByUpperId(upperCodeId));
    }

    @GetMapping("/group/code/{upperCode}")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperCode(@PathVariable String upperCode) {
        return ResponseEntity.ok(codeQueryService.getCodesByUpperCode(upperCode));
    }

}
