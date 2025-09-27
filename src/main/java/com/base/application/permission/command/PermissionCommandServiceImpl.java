package com.base.application.permission.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.permission.dto.PermissionRequest;
import com.base.api.permission.dto.PermissionResponse;
import com.base.api.permission.mapper.PermissionMapper;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PermissionCommandServiceImpl implements PermissionCommandService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByPermissionCode(request.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + request.permissionCode());
        }
        Permission permission = permissionMapper.toEntity(request);
        return permissionMapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found"));

        if (!existing.getPermissionCode().equals(request.permissionCode())
                && permissionRepository.existsByPermissionCode(request.permissionCode())) {
            throw new ConflictException("Permission code already exists: " + request.permissionCode());
        }

        permissionMapper.updateFromRequest(request, existing);
        return permissionMapper.toResponse(permissionRepository.save(existing));
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found"));
        existing.setUseYn(false); // soft delete
        permissionRepository.save(existing);
    }
    
}
