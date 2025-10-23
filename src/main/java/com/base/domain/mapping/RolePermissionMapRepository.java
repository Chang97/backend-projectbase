package com.base.domain.mapping;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, RolePermissionId> {

    @Query("SELECT rpm.permission.permissionId FROM RolePermissionMap rpm WHERE rpm.role.roleId = :roleId")
    List<Long> findPermissionIdsByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT rpm.role.roleId AS roleId, rpm.permission.permissionId AS permissionId FROM RolePermissionMap rpm WHERE rpm.role.roleId IN :roleIds")
    List<Object[]> findMappingsByRoleIds(@Param("roleIds") Collection<Long> roleIds);

    @Query("SELECT DISTINCT rpm.role.roleId FROM RolePermissionMap rpm WHERE rpm.permission.permissionId = :permissionId")
    List<Long> findRoleIdsByPermissionId(@Param("permissionId") Long permissionId);

    @Modifying
    void deleteByRoleRoleId(Long roleId);
}
