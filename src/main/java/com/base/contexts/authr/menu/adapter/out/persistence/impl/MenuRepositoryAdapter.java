package com.base.contexts.authr.menu.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.contexts.authr.menu.adapter.out.persistence.mapper.MenuEntityMapper;
import com.base.contexts.authr.menu.adapter.out.persistence.repo.MenuJpaRepository;
import com.base.contexts.authr.menu.adapter.out.persistence.spec.MenuEntitySpecifications;
import com.base.contexts.authr.menu.domain.model.Menu;
import com.base.contexts.authr.menu.domain.model.MenuFilter;
import com.base.contexts.authr.menu.domain.port.out.MenuCommandPort;
import com.base.contexts.authr.menu.domain.port.out.MenuQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuRepositoryAdapter implements MenuCommandPort, MenuQueryPort {

    private final MenuJpaRepository jpaRepository;
    private final MenuEntityMapper mapper;

    @Override
    public Menu save(Menu menu) {
        MenuEntity entity;
        if (menu.getMenuId() == null) {
            entity = mapper.toNewEntity(menu);
        } else {
            Long menuId = menu.getMenuId().value();
            entity = jpaRepository.findById(menuId)
                    .orElseThrow(() -> new NotFoundException("Menu not found: " + menuId));
            mapper.merge(menu, entity);
        }
        if (menu.getUpperMenuId() != null) {
            MenuEntity upper = jpaRepository.getReferenceById(menu.getUpperMenuId().value());
            entity.setUpperMenu(upper);
        } else {
            entity.setUpperMenu(null);
        }
        MenuEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Menu> findById(Long menuId) {
        return jpaRepository.findById(menuId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsById(Long menuId) {
        return jpaRepository.existsById(menuId);
    }

    @Override
        public boolean existsByMenuCode(String menuCode) {
        return jpaRepository.existsByMenuCode(menuCode);
    }

    @Override
        public List<Menu> findByUpperMenuId(Long upperMenuId) {
        return jpaRepository.findByUpperMenu_MenuId(upperMenuId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
        public List<Menu> search(MenuFilter filter) {
        return jpaRepository.findAll(MenuEntitySpecifications.withFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
        public List<Menu> findAccessibleMenusByUserId(Long userId) {
        return jpaRepository.findAccessibleMenusByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
