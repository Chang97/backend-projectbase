package com.base.api.org;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.application.org.command.OrgCommandService;
import com.base.application.org.query.OrgQueryService;
import com.base.domain.org.Org;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrgController {
    
    private final OrgCommandService orgCommandService;
    private final OrgQueryService orgQueryService;

    // 조회
    @GetMapping
    public List<Org> getAllOrgs() {
        return orgQueryService.getOrgs();
    }

    @GetMapping("/{id}")
    public Org getOrg(@PathVariable Long id) {
        return orgQueryService.getOrg(id);
    }

    // 생성
    @PostMapping
    public Org createOrg(@RequestBody Org org) {
        return orgCommandService.createOrg(org);
    }

    // 수정
    @PutMapping("/{id}")
    public Org updateOrg(@PathVariable Long id, @RequestBody Org org) {
        return orgCommandService.updateOrg(id, org);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteOrg(@PathVariable Long id) {
        orgCommandService.deleteOrg(id);
    }
}
