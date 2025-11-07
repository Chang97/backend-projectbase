package com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.entity.RolePermissionMapEntity;
import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.mapper.RolePermissionMapEntityMapper;
import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.repo.RolePermissionMapJpaRepository;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMapId;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapCommandPort;
import com.base.contexts.authr.rolepermissionmap.domain.port.out.RolePermissionMapQueryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class RolePermissionMapRepositoryAdapter implements RolePermissionMapCommandPort, RolePermissionMapQueryPort {

    private final RolePermissionMapJpaRepository jpaRepository;
    private final RolePermissionMapEntityMapper mapper;

    @Override
    public RolePermissionMap save(RolePermissionMap rolePermission) {
        RolePermissionMapEntity entity = mapper.toEntity(rolePermission);
        RolePermissionMapEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void saveAll(Collection<RolePermissionMap> rolePermissions) {
        List<RolePermissionMapEntity> entities = rolePermissions.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        jpaRepository.saveAll(entities);
    }

    @Override
    public void delete(RolePermissionMapId id) {
        jpaRepository.deleteById(mapper.toEntityId(id));
    }

    @Override
    public void deleteAllByRoleId(Long roleId) {
        jpaRepository.deleteByRoleId(roleId);
    }

    @Override
    public Optional<RolePermissionMap> findById(RolePermissionMapId id) {
        return jpaRepository.findById(mapper.toEntityId(id))
                .map(mapper::toDomain);
    }

    @Override
    public List<RolePermissionMap> findByRoleId(Long roleId) {
        return jpaRepository.findByRoleId(roleId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<RolePermissionMap> findByPermissionId(Long permissionId) {
        return jpaRepository.findByPermissionId(permissionId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<RolePermissionMap> findByRoleIds(Collection<Long> roleIds) {
        return jpaRepository.findByRoleIds(roleIds).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Long> findRoleIdsByPermissionId(Long permissionId) {
        return jpaRepository.findRoleIdsByPermissionId(permissionId);
    }
}
