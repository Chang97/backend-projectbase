package com.base.contexts.authr.rolepermissionmap.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMapId;

public interface RolePermissionMapCommandPort {

    RolePermissionMap save(RolePermissionMap rolePermission);

    void saveAll(Collection<RolePermissionMap> rolePermissions);

    void delete(RolePermissionMapId id);

    void deleteAllByRoleId(Long roleId);

    Optional<RolePermissionMap> findById(RolePermissionMapId id);

    List<RolePermissionMap> findByPermissionId(Long permissionId);

    List<Long> findRoleIdsByPermissionId(Long permissionId);
}
