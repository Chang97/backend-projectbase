package com.base.application.role.command;

import org.springframework.stereotype.Service;

import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        existing.setRoleName(role.getRoleName());
        existing.setUseYn(role.getUseYn());
        return roleRepository.save(existing);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    
}
