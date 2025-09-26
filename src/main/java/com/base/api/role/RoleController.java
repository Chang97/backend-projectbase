package com.base.api.role;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.application.role.command.RoleCommandService;
import com.base.application.role.query.RoleQueryService;
import com.base.domain.role.Role;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleCommandService roleCommandService;
    private final RoleQueryService roleQueryService;

    // 조회
    @GetMapping
    public List<Role> getRoles() {
        return roleQueryService.getRoles();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleQueryService.getRole(id);
    }

    // 생성
    @PostMapping("/{id}")
    public Role createRole(@PathVariable Long id, @RequestBody Role role) {
        return roleCommandService.createRole(role);
    }

    // 수정
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleCommandService.updateRole(id, role);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleCommandService.deleteRole(id);
    }
}
