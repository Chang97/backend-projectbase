package com.base.application.permission.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.permission.dto.PermissionResponse;
import com.base.api.permission.mapper.PermissionMapper;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionQueryServiceImpl implements PermissionQueryService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;


    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermission(Long id) {
        return permissionMapper.toResponse(permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getPermissions() {
        return permissionMapper.toResponseList(permissionRepository.findAll());
    }
}
