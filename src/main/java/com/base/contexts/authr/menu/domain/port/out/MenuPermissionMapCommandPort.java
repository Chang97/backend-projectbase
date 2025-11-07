package com.base.contexts.authr.menu.domain.port.out;

import java.util.Collection;

import com.base.contexts.authr.menu.domain.model.MenuPermissionMap;

public interface MenuPermissionMapCommandPort {

    void replacePermissions(Long menuId, Collection<MenuPermissionMap> permissions);
}
