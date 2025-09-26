package com.base.api.code;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.application.code.command.CodeCommandService;
import com.base.application.code.query.CodeQueryService;
import com.base.domain.code.Code;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeController {
    
    private final CodeCommandService codeCommandService;
    private final CodeQueryService codeQueryService;

    // 조회
    @GetMapping
    public List<Code> getAllCodes() {
        return codeQueryService.getCodes();
    }

    @GetMapping("/{id}")
    public Code getCode(@PathVariable Long id) {
        return codeQueryService.getCode(id);
    }

    // 생성
    @PostMapping
    public Code createCode(@RequestBody Code code) {
        return codeCommandService.createCode(code);
    }

    // 수정
    @PutMapping("/{id}")
    public Code updateCode(@PathVariable Long id, @RequestBody Code code) {
        return codeCommandService.updateCode(id, code);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteCode(@PathVariable Long id) {
        codeCommandService.deleteCode(id);
    }
}
