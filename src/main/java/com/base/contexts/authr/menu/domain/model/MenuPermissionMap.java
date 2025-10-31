package com.base.contexts.authr.menu.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuPermissionMap {

    private final MenuPermissionMapId id;
    private final Long menuId;
    private final Long permissionId;

    public static MenuPermissionMap of(Long menuId, Long permissionId) {
        MenuPermissionMapId id = MenuPermissionMapId.of(menuId, permissionId);
        return new MenuPermissionMap(id, menuId, permissionId);
    }
}
