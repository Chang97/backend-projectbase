package com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.entity.RolePermissionMapEntity;
import com.base.contexts.authr.rolepermissionmap.adapter.out.persistence.entity.RolePermissionMapEntityId;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMap;
import com.base.contexts.authr.rolepermissionmap.domain.model.RolePermissionMapId;

@Component
public class RolePermissionMapEntityMapper {

    public RolePermissionMapEntity toEntity(RolePermissionMap domain) {
        RolePermissionMapEntity entity = new RolePermissionMapEntity();
        entity.setId(toEntityId(domain.getId()));
        entity.setRoleId(domain.getRoleId());
        entity.setPermissionId(domain.getPermissionId());
        return entity;
    }

    public RolePermissionMap toDomain(RolePermissionMapEntity entity) {
        RolePermissionMapId id = RolePermissionMapId.of(entity.getRoleId(), entity.getPermissionId());
        return RolePermissionMap.of(id.roleId(), id.permissionId());
    }

    public RolePermissionMapEntityId toEntityId(RolePermissionMapId id) {
        return new RolePermissionMapEntityId(id.roleId(), id.permissionId());
    }
}
