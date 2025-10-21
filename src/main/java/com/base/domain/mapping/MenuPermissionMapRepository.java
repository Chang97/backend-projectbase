package com.base.domain.mapping;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuPermissionMapRepository extends JpaRepository<MenuPermissionMap, MenuPermissionId> {

    @Query("SELECT mpm.menu.menuId FROM MenuPermissionMap mpm WHERE mpm.permission.permissionId = :permissionId")
    List<Long> findMenuIdsByPermissionId(@Param("permissionId") Long permissionId);

    @Query("SELECT mpm.permission.permissionId FROM MenuPermissionMap mpm WHERE mpm.menu.menuId = :menuId")
    List<Long> findPermissionIdsByMenuId(@Param("menuId") Long menuId);

    @Query("SELECT mpm.menu.menuId AS menuId, mpm.permission.permissionId AS permissionId " +
           "FROM MenuPermissionMap mpm WHERE mpm.menu.menuId IN :menuIds")
    List<Object[]> findPermissionMappings(@Param("menuIds") Collection<Long> menuIds);

    @Modifying
    void deleteByMenuMenuId(Long menuId);
}
