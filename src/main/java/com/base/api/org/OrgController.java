package com.base.api.org;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.org.dto.OrgRequest;
import com.base.api.org.dto.OrgResponse;
import com.base.application.org.command.OrgCommandService;
import com.base.application.org.query.OrgQueryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrgController {
    
    private final OrgCommandService orgCommandService;
    private final OrgQueryService orgQueryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ORG_CREATE')")
    public ResponseEntity<OrgResponse> createOrg(@RequestBody OrgRequest request) {
        return ResponseEntity.ok(orgCommandService.createOrg(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORG_UPDATE')")
    public ResponseEntity<OrgResponse> updateOrg(
            @PathVariable Long id, @RequestBody OrgRequest request) {
        return ResponseEntity.ok(orgCommandService.updateOrg(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORG_DELETE')")
    public ResponseEntity<Void> deleteOrg(@PathVariable Long id) {
        orgCommandService.deleteOrg(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ORG_READ')")
    public ResponseEntity<OrgResponse> getOrg(@PathVariable Long id) {
        return ResponseEntity.ok(orgQueryService.getOrg(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORG_LIST')")
    public ResponseEntity<List<OrgResponse>> getOrgs() {
        return ResponseEntity.ok(orgQueryService.getOrgs());
    }

    @GetMapping("/group/{upperOrgId}")
    @PreAuthorize("hasAuthority('ORG_CHILDREN_LIST')")
    public ResponseEntity<List<OrgResponse>> getOrgsByUpperId(@PathVariable Long upperOrgId) {
        return ResponseEntity.ok(orgQueryService.getOrgsByUpperId(upperOrgId));
    }

    @GetMapping("/group/code/{upperOrg}")
    @PreAuthorize("hasAuthority('ORG_CHILDREN_BY_CODE')")
    public ResponseEntity<List<OrgResponse>> getOrgsByUpperOrg(@PathVariable String upperOrg) {
        return ResponseEntity.ok(orgQueryService.getOrgsByUpperOrg(upperOrg));
    }
}
