package com.base.contexts.authr.permission.adapter.out.persistence.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.permission.adapter.out.persistence.mapper.PermissionEntityMapper;
import com.base.contexts.authr.permission.adapter.out.persistence.repo.PermissionJpaRepository;
import com.base.contexts.authr.permission.adapter.out.persistence.spec.PermissionEntitySpecifications;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;
import com.base.contexts.authr.permission.domain.port.out.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
class PermissionRepositoryAdapter implements PermissionRepository {

    private final PermissionJpaRepository jpaRepository;
    private final PermissionEntityMapper mapper;

    @Override
    public Permission save(Permission permission) {
        if (permission.getPermissionId() == null) {
            return mapper.toDomain(jpaRepository.save(mapper.toNewEntity(permission)));
        }
        Long permissionId = permission.getPermissionId().permissionId();
        return jpaRepository.findById(permissionId)
                .map(entity -> {
                    mapper.merge(permission, entity);
                    return mapper.toDomain(jpaRepository.save(entity));
                })
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findById(Long permissionId) {
        return jpaRepository.findById(permissionId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByPermission(String permissionCode) {
        return jpaRepository.findByPermissionCode(permissionCode)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findAllByIds(Collection<Long> permissionIds) {
        return jpaRepository.findAllById(permissionIds).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByPermissionCode(String permissionCode) {
        return jpaRepository.findByPermissionCode(permissionCode)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPermissionCode(String permissionCode) {
        return jpaRepository.existsByPermissionCode(permissionCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findAll(PermissionFilter filter) {
        return jpaRepository.findAll(PermissionEntitySpecifications.withFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
