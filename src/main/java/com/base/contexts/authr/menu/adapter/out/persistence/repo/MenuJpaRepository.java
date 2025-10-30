package com.base.contexts.authr.menu.adapter.out.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuEntity;

import io.lettuce.core.dynamic.annotation.Param;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long>, JpaSpecificationExecutor<MenuEntity> {

    Optional<MenuEntity> findByMenuCode(String menuCode);

    boolean existsByMenuCode(String menuCode);

    List<MenuEntity> findByUpperMenu_MenuId(Long upperMenuId);

    List<MenuEntity> findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(Long upperMenuId);

    List<MenuEntity> findByUpperMenu_MenuCodeAndUseYnTrueOrderBySrtAsc(String upperMenuCode);

    @Query("""
            SELECT DISTINCT m
            FROM MenuPermissionMapEntity mpm
            JOIN mpm.menu m
            JOIN mpm.permission p
            JOIN RolePermissionMapEntity rpm ON rpm.permission = p
            JOIN rpm.role r
            JOIN UserRoleMapEntity urm ON urm.role = r
            JOIN urm.user u
            WHERE u.userId = :userId
              AND m.useYn = true
              AND COALESCE(r.useYn, true) = true
              AND COALESCE(p.useYn, true) = true
              AND COALESCE(u.useYn, true) = true
            """)
    List<MenuEntity> findAccessibleMenusByUserId(@Param("userId") Long userId);
}
