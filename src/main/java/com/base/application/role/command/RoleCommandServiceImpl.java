package com.base.application.role.command;

import org.springframework.stereotype.Service;

import com.base.api.role.dto.RoleRequest;
import com.base.api.role.dto.RoleResponse;
import com.base.api.role.mapper.RoleMapper;
import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Validated
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByRoleName(request.roleName())) {
            throw new ConflictException("Role name already exists: " + request.roleName());
        }
        Role role = roleMapper.toEntity(request);
        if (role.getUseYn() == null) {
            role.setUseYn(Boolean.TRUE);
        }
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        // 자기 자신이 아닌 다른 레코드에서 중복 확인
        if (!existing.getRoleName().equals(request.roleName())
                && roleRepository.existsByRoleName(request.roleName())) {
            throw new ConflictException("Role name already exists: " + request.roleName());
        }

        existing.setRoleName(request.roleName());
        if (request.useYn() != null) {
            existing.setUseYn(request.useYn());
        }
        return roleMapper.toResponse(roleRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        existing.setUseYn(false); // Soft delete 처리
        roleRepository.save(existing);
    }

    
}
