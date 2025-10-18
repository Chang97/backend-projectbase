package com.base.api.atchFile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.atchFile.dto.AtchFileRequest;
import com.base.api.atchFile.dto.AtchFileResponse;
import com.base.application.atchFile.command.AtchFileCommandService;
import com.base.application.atchFile.query.AtchFileQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/AtchFile")
@RequiredArgsConstructor
public class AtchFileController {

    private final AtchFileCommandService atchFileCommandService;
    private final AtchFileQueryService atchFileQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ATCH_FILE_CREATE')")
    public ResponseEntity<AtchFileResponse> create(@RequestBody AtchFileRequest request) {
        return ResponseEntity.ok(atchFileCommandService.createAtchFile(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ATCH_FILE_UPDATE')")
    public ResponseEntity<AtchFileResponse> update(@PathVariable Long id, @RequestBody AtchFileRequest request) {
        return ResponseEntity.ok(atchFileCommandService.updateAtchFile(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ATCH_FILE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atchFileCommandService.deleteAtchFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ATCH_FILE_READ')")
    public ResponseEntity<AtchFileResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(atchFileQueryService.getAtchFile(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ATCH_FILE_LIST')")
    public ResponseEntity<List<AtchFileResponse>> getList() {
        return ResponseEntity.ok(atchFileQueryService.getAtchFileList());
    }
}
