package com.base.contexts.authr.rolepermissionmap.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMapId;

public interface RolePermissionMapRepository {

    RolePermissionMap save(RolePermissionMap rolePermission);

    void saveAll(Collection<RolePermissionMap> rolePermissions);

    void delete(RolePermissionMapId id);

    void deleteAllByRoleId(Long roleId);

    Optional<RolePermissionMap> findById(RolePermissionMapId id);

    List<RolePermissionMap> findByRoleId(Long roleId);

    List<RolePermissionMap> findByPermissionId(Long permissionId);

    List<RolePermissionMap> findByRoleIds(Collection<Long> roleIds);

    List<Long> findRoleIdsByPermissionId(Long permissionId);

}
