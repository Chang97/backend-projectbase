package com.base.application.permission.command;

import org.springframework.stereotype.Service;

import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PermissionCommandServiceImpl implements PermissionCommandService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.setPermissionName(permission.getPermissionName());
        existing.setUseYn(permission.getUseYn());
        return permissionRepository.save(existing);
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    
}
