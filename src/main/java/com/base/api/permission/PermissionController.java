package com.base.api.permission;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.application.permission.command.PermissionCommandService;
import com.base.application.permission.query.PermissionQueryService;
import com.base.domain.permission.Permission;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    
    private final PermissionCommandService permissionCommandService;
    private final PermissionQueryService permissionQueryService;

    // 조회
    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionQueryService.getPermissions();
    }

    @GetMapping("/{id}")
    public Permission getPermission(@PathVariable Long id) {
        return permissionQueryService.getPermission(id);
    }

    // 생성
    @PostMapping
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionCommandService.createPermission(permission);
    }

    // 수정
    @PutMapping("/{id}")
    public Permission updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionCommandService.updatePermission(id, permission);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable Long id) {
        permissionCommandService.deletePermission(id);
    }
}
