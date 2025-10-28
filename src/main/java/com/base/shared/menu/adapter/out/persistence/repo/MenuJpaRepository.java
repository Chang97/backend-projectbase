package com.base.shared.menu.adapter.out.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.shared.menu.adapter.out.persistence.entity.MenuEntity;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long>, JpaSpecificationExecutor<MenuEntity> {

    Optional<MenuEntity> findByMenuCode(String menuCode);

    boolean existsByMenuCode(String menuCode);

    List<MenuEntity> findByUpperMenu_MenuId(Long upperMenuId);
}
