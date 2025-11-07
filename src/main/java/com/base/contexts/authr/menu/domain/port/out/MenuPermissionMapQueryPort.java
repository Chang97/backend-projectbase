package com.base.contexts.authr.menu.domain.port.out;

import java.util.Collection;
import java.util.List;

import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;

public interface MenuPermissionMapQueryPort {

    List<Long> findPermissionIdsByMenuId(Long menuId);

    List<MenuPermissionMap> findByMenuIds(Collection<Long> menuIds);

    List<Long> findMenuIdsByPermissionId(Long permissionId);
}
