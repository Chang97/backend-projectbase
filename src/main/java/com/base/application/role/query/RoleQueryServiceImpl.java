package com.base.application.role.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
