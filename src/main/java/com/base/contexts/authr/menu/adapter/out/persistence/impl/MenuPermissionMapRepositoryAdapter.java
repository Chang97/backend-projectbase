package com.base.contexts.authr.menu.adapter.out.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuPermissionMapEntity;
import com.base.contexts.authr.menu.adapter.out.persistence.mapper.MenuPermissionMapEntityMapper;
import com.base.contexts.authr.menu.adapter.out.persistence.repo.MenuJpaRepository;
import com.base.contexts.authr.menu.adapter.out.persistence.repo.MenuPermissionMapJpaRepository;
import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;
import com.base.contexts.authr.menu.domain.port.out.MenuPermissionMapRepository;
import com.base.contexts.authr.permission.adapter.out.persistence.repo.PermissionJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
class MenuPermissionMapRepositoryAdapter implements MenuPermissionMapRepository {

    private final MenuPermissionMapJpaRepository jpaRepository;
    private final MenuPermissionMapEntityMapper mapper;
    private final MenuJpaRepository menuJpaRepository;
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public void replacePermissions(Long menuId, Collection<MenuPermissionMap> permissions) {
        jpaRepository.deleteByMenuId(menuId);
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        MenuEntity menuRef = menuJpaRepository.getReferenceById(menuId);
        List<MenuPermissionMapEntity> entities = permissions.stream()
                .map(mapper::toEntity)
                .peek(entity -> mapper.attachMenu(entity, menuRef))
                .peek(entity -> mapper.attachPermission(entity,
                        permissionJpaRepository.getReferenceById(entity.getPermissionId())))
                .toList();
        jpaRepository.saveAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findPermissionIdsByMenuId(Long menuId) {
        return jpaRepository.findPermissionIdsByMenuId(menuId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuPermissionMap> findByMenuIds(Collection<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return List.of();
        }
        return jpaRepository.findByMenuIds(menuIds).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findMenuIdsByPermissionId(Long permissionId) {
        return jpaRepository.findMenuIdsByPermissionId(permissionId);
    }
}
