package com.base.infra.persistence.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.application.role.port.out.RolePersistencePort;
import com.base.application.role.usecase.query.condition.RoleSearchCondition;
import com.base.domain.role.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaRolePersistenceAdapter implements RolePersistencePort {

    private final JpaRoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Override
    public List<Role> search(RoleSearchCondition condition) {
        RoleSearchCondition criteria = condition != null ? condition : new RoleSearchCondition();
        criteria.normalize();
        return roleRepository.findAll(RoleSpecifications.withCondition(criteria));
    }
}
