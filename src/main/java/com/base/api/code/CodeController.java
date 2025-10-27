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
import com.base.api.code.assembler.CodeCommandAssembler;
import com.base.api.code.assembler.CodeResponseAssembler;
import com.base.application.code.port.in.CreateCodeUseCase;
import com.base.application.code.port.in.DeleteCodeUseCase;
import com.base.application.code.port.in.GetCodeUseCase;
import com.base.application.code.port.in.GetCodesByUpperCodeUseCase;
import com.base.application.code.port.in.GetCodesByUpperIdUseCase;
import com.base.application.code.port.in.GetCodesUseCase;
import com.base.application.code.port.in.UpdateCodeUseCase;
import com.base.application.code.usecase.command.CreateCodeCommand;
import com.base.application.code.usecase.command.UpdateCodeCommand;
import com.base.application.code.usecase.query.condition.CodeSearchCondition;
import com.base.application.code.usecase.result.CodeResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {
    
    private final CreateCodeUseCase createCodeUseCase;
    private final UpdateCodeUseCase updateCodeUseCase;
    private final DeleteCodeUseCase deleteCodeUseCase;
    private final GetCodeUseCase getCodeUseCase;
    private final GetCodesUseCase getCodesUseCase;
    private final GetCodesByUpperIdUseCase getCodesByUpperIdUseCase;
    private final GetCodesByUpperCodeUseCase getCodesByUpperCodeUseCase;
    private final CodeCommandAssembler codeCommandAssembler;
    private final CodeResponseAssembler codeResponseAssembler;

    @PostMapping
    @PreAuthorize("hasAuthority('CODE_CREATE')")
    public ResponseEntity<CodeResponse> createCode(@Valid @RequestBody CodeRequest request) {
        CreateCodeCommand command = codeCommandAssembler.toCreateCommand(request);
        CodeResult result = createCodeUseCase.handle(command);
        return ResponseEntity.ok(codeResponseAssembler.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_UPDATE')")
    public ResponseEntity<CodeResponse> updateCode(@PathVariable Long id, @Valid @RequestBody CodeRequest request) {
        UpdateCodeCommand command = codeCommandAssembler.toUpdateCommand(request);
        CodeResult result = updateCodeUseCase.handle(id, command);
        return ResponseEntity.ok(codeResponseAssembler.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_DELETE')")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        deleteCodeUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_READ')")
    public ResponseEntity<CodeResponse> getCode(@PathVariable Long id) {
        CodeResult result = getCodeUseCase.handle(id);
        return ResponseEntity.ok(codeResponseAssembler.toResponse(result));
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
        return ResponseEntity.ok(codeResponseAssembler.toResponses(getCodesUseCase.handle(condition)));
    }

    @GetMapping("/group/{upperCodeId}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_ID')")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperId(@PathVariable Long upperCodeId) {
        return ResponseEntity.ok(codeResponseAssembler.toResponses(getCodesByUpperIdUseCase.handle(upperCodeId)));
    }

    @GetMapping("/group/code/{upperCode}")
    @PreAuthorize("hasAuthority('CODE_GROUP_BY_CODE')")
    public ResponseEntity<List<CodeResponse>> getCodesByUpperCode(@PathVariable String upperCode) {
        return ResponseEntity.ok(codeResponseAssembler.toResponses(getCodesByUpperCodeUseCase.handle(upperCode)));
    }

}
