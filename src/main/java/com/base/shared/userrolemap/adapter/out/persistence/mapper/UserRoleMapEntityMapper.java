package com.base.shared.userrolemap.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.shared.userrolemap.adapter.out.persistence.entity.UserRoleMapEntity;
import com.base.shared.userrolemap.adapter.out.persistence.entity.UserRoleMapEntityId;
import com.base.shared.userrolemap.domain.model.UserRoleMap;
import com.base.shared.userrolemap.domain.model.UserRoleMapId;

@Component
public class UserRoleMapEntityMapper {

    public UserRoleMapEntity toEntity(UserRoleMap domain) {
        UserRoleMapEntity entity = new UserRoleMapEntity();
        entity.setId(toEntityId(domain.getId()));
        entity.setUserId(domain.getUserId());
        entity.setRoleId(domain.getRoleId());
        return entity;
    }

    public UserRoleMap toDomain(UserRoleMapEntity entity) {
        UserRoleMapId id = UserRoleMapId.of(entity.getUserId(), entity.getRoleId());
        return UserRoleMap.of(id.userId(), id.roleId());
    }

    public UserRoleMapEntityId toEntityId(UserRoleMapId id) {
        return new UserRoleMapEntityId(id.userId(), id.roleId());
    }
}
