package com.base.application.role.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.role.dto.RoleResponse;
import com.base.api.role.mapper.RoleMapper;
import com.base.domain.role.RoleRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRole(Long id) {
        return roleMapper.toResponse(roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getRoles(RoleSearchCondition condition) {
        if (condition != null) {
            condition.normalize();
        }
        return roleRepository.findAll(RoleSpecifications.withCondition(condition)).stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}
