package com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.repo;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.entity.RolePermissionMapEntity;
import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.entity.RolePermissionMapEntityId;

public interface RolePermissionMapJpaRepository extends JpaRepository<RolePermissionMapEntity, RolePermissionMapEntityId> {

    @Query("""
            SELECT entity
            FROM RolePermissionMapEntity entity
            WHERE entity.roleId = :roleId
            """)
    List<RolePermissionMapEntity> findByRoleId(@Param("roleId") Long roleId);

    @Query("""
            SELECT entity
            FROM RolePermissionMapEntity entity
            WHERE entity.permissionId = :permissionId
            """)
    List<RolePermissionMapEntity> findByPermissionId(@Param("permissionId") Long permissionId);

    @Query("""
            SELECT entity
            FROM RolePermissionMapEntity entity
            WHERE entity.roleId IN :roleIds
            """)
    List<RolePermissionMapEntity> findByRoleIds(@Param("roleIds") Collection<Long> roleIds);

    @Query("""
            SELECT DISTINCT entity.roleId
            FROM RolePermissionMapEntity entity
            WHERE entity.permissionId = :permissionId
            """)
    List<Long> findRoleIdsByPermissionId(@Param("permissionId") Long permissionId);

    @Modifying
    void deleteByRoleId(@Param("roleId") Long roleId);
}
