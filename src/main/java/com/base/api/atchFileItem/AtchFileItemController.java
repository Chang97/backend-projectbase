package com.base.api.atchFileItem;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.atchFileItem.dto.AtchFileItemRequest;
import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.application.atchFileItem.command.AtchFileItemCommandService;
import com.base.application.atchFileItem.query.AtchFileItemQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/entityPath")
@RequiredArgsConstructor
public class AtchFileItemController {

    private final AtchFileItemCommandService atchFileItemCommandService;
    private final AtchFileItemQueryService atchFileItemQueryService;

    @PostMapping
    public ResponseEntity<AtchFileItemResponse> create(@RequestBody AtchFileItemRequest request) {
        return ResponseEntity.ok(atchFileItemCommandService.createAtchFileItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtchFileItemResponse> update(@PathVariable Long id, @RequestBody AtchFileItemRequest request) {
        return ResponseEntity.ok(atchFileItemCommandService.updateAtchFileItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atchFileItemCommandService.deleteAtchFileItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtchFileItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(atchFileItemQueryService.getAtchFileItem(id));
    }

    @GetMapping
    public ResponseEntity<List<AtchFileItemResponse>> getList() {
        return ResponseEntity.ok(atchFileItemQueryService.getAtchFileItemList());
    }
}