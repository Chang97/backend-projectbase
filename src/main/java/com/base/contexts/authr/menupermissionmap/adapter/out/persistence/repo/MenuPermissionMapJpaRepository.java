package com.base.contexts.authr.menupermissionmap.adapter.out.persistence.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.base.contexts.authr.menupermissionmap.adapter.out.persistence.entity.MenuPermissionMapEntity;
import com.base.contexts.authr.menupermissionmap.adapter.out.persistence.entity.MenuPermissionMapEntityId;

public interface MenuPermissionMapJpaRepository extends JpaRepository<MenuPermissionMapEntity, MenuPermissionMapEntityId> {

    @Query("""
            SELECT entity
            FROM MenuPermissionMapEntity entity
            WHERE entity.menuId = :menuId
            """)
    List<MenuPermissionMapEntity> findByMenuId(@Param("menuId") Long menuId);

    @Query("""
            SELECT entity.permissionId
            FROM MenuPermissionMapEntity entity
            WHERE entity.menuId = :menuId
            """)
    List<Long> findPermissionIdsByMenuId(@Param("menuId") Long menuId);

    @Query("""
            SELECT entity
            FROM MenuPermissionMapEntity entity
            WHERE entity.menuId IN :menuIds
            """)
    List<MenuPermissionMapEntity> findByMenuIds(@Param("menuIds") Collection<Long> menuIds);

    @Query("""
            SELECT entity.menuId
            FROM MenuPermissionMapEntity entity
            WHERE entity.permissionId = :permissionId
            """)
    List<Long> findMenuIdsByPermissionId(@Param("permissionId") Long permissionId);

    @Modifying
    void deleteByMenuId(Long menuId);
}
