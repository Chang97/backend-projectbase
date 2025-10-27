package com.base.application.permission.port.out;

import java.util.Collection;
import java.util.List;

import com.base.domain.permission.Permission;

public interface PermissionLookupPort {

    List<Permission> findAllByIds(Collection<Long> permissionIds);
}
