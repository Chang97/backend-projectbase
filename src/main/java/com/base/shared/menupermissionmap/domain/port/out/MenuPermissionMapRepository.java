package com.base.shared.menupermissionmap.domain.port.out;

import java.util.Collection;
import java.util.List;

import com.base.shared.menupermissionmap.domain.model.MenuPermissionMap;

public interface MenuPermissionMapRepository {

    void replacePermissions(Long menuId, Collection<MenuPermissionMap> permissions);

    List<Long> findPermissionIdsByMenuId(Long menuId);

    List<MenuPermissionMap> findByMenuIds(Collection<Long> menuIds);

    List<Long> findMenuIdsByPermissionId(Long permissionId);
}
