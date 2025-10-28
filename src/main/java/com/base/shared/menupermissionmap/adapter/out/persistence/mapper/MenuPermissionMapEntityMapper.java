package com.base.shared.menupermissionmap.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.shared.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.shared.menupermissionmap.adapter.out.persistence.entity.MenuPermissionMapEntity;
import com.base.shared.menupermissionmap.adapter.out.persistence.entity.MenuPermissionMapEntityId;
import com.base.shared.menupermissionmap.domain.model.MenuPermissionMap;
import com.base.shared.permission.adapter.out.persistence.entity.PermissionEntity;

@Component
public class MenuPermissionMapEntityMapper {

    public MenuPermissionMapEntity toEntity(MenuPermissionMap domain) {
        MenuPermissionMapEntity entity = new MenuPermissionMapEntity();
        entity.setId(new MenuPermissionMapEntityId(domain.getMenuId(), domain.getPermissionId()));
        entity.setMenuId(domain.getMenuId());
        entity.setPermissionId(domain.getPermissionId());
        return entity;
    }

    public void attachMenu(MenuPermissionMapEntity entity, MenuEntity menu) {
        entity.setMenu(menu);
    }

    public void attachPermission(MenuPermissionMapEntity entity, PermissionEntity permission) {
        entity.setPermission(permission);
    }

    public MenuPermissionMap toDomain(MenuPermissionMapEntity entity) {
        return MenuPermissionMap.of(entity.getMenuId(), entity.getPermissionId());
    }
}
