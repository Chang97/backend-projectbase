package com.base.application.permission.query;

import java.util.List;

import org.springframework.data.domain.Sort;
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
    public List<PermissionResponse> getPermissions(PermissionSearchCondition condition) {
        PermissionSearchCondition criteria = condition != null ? condition : new PermissionSearchCondition();
        criteria.normalize();

        return permissionRepository.findAll(
                    PermissionSpecifications.withCondition(criteria),
                    Sort.by(Sort.Order.asc("permissionCode"))
                )
            .stream()
            .map(permissionMapper::toResponse)
            .toList();
    }
}
