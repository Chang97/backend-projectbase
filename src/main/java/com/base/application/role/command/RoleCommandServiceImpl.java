package com.base.application.role.command;

import org.springframework.stereotype.Service;

import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.exception.BusinessException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            throw new BusinessException("Role name already exists: " + role.getRoleName());
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Role not found"));

        // 자기 자신이 아닌 다른 레코드에서 중복 확인
        if (!existing.getRoleName().equals(role.getRoleName())
                && roleRepository.existsByRoleName(role.getRoleName())) {
            throw new BusinessException("Role name already exists: " + role.getRoleName());
        }

        existing.setRoleName(role.getRoleName());
        existing.setUseYn(role.getUseYn());
        return roleRepository.save(existing);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    
}
