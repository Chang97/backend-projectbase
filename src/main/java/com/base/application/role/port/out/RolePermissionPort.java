package com.base.application.role.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.domain.permission.Permission;
import com.base.domain.role.Role;

public interface RolePermissionPort {

    void clearPermissions(Long roleId);

    void assignPermissions(Role role, List<Permission> permissions);

    List<Long> findPermissionIdsByRoleId(Long roleId);

    Map<Long, List<Long>> findPermissionIdsByRoleIds(Collection<Long> roleIds);
}
