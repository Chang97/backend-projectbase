package com.base.shared.role.adapter.out.persistence.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.shared.role.adapter.out.persistence.entity.RoleEntity;
import com.base.shared.role.adapter.out.persistence.mapper.RoleEntityMapper;
import com.base.shared.role.adapter.out.persistence.repo.RoleJpaRepository;
import com.base.shared.role.adapter.out.persistence.spec.RoleEntitySpecifications;
import com.base.shared.role.domain.model.Role;
import com.base.shared.role.domain.model.RoleFilter;
import com.base.shared.role.domain.port.out.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
class RoleRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository jpaRepository;
    private final RoleEntityMapper mapper;

    @Override
    public Role save(Role role) {
        RoleEntity entity;
        if (role.getRoleId() == null) {
            entity = mapper.toNewEntity(role);
        } else {
            Long roleId = role.getRoleId().value();
            entity = jpaRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));
            mapper.merge(role, entity);
        }
        RoleEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long roleId) {
        return jpaRepository.findById(roleId).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String roleName) {
        return jpaRepository.findByRoleNameIgnoreCase(roleName).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String roleName) {
        return jpaRepository.existsByRoleNameIgnoreCase(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllByIds(Collection<Long> roleIds) {
        return jpaRepository.findAllById(roleIds).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> search(RoleFilter filter) {
        return jpaRepository.findAll(RoleEntitySpecifications.withFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
