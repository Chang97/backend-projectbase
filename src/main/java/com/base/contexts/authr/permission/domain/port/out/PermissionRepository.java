package com.base.contexts.authr.permission.domain.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;

public interface PermissionRepository {
    Permission save(Permission permission);

    Optional<Permission> findById(Long codeId);

    Optional<Permission> findByPermission(String code);

    List<Permission> findAllByIds(Collection<Long> permissionIds);

    Optional<Permission> findByPermissionCode(String permissionCode);

    boolean existsByPermissionCode(String permissionCode);

    List<Permission> findAll(PermissionFilter filter);
}
