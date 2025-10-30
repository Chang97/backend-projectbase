package com.base.contexts.authr.role.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.role.adapter.out.persistence.entity.RoleEntity;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.model.RoleId;

@Component
public class RoleEntityMapper {

    public RoleEntity toNewEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        apply(role, entity);
        return entity;
    }

    public void merge(Role role, RoleEntity target) {
        apply(role, target);
    }

    private void apply(Role role, RoleEntity target) {
        target.setRoleName(role.getRoleName());
        target.setUseYn(role.getUseYn());
    }

    public Role toDomain(RoleEntity entity) {
        return Role.restore(
                RoleId.of(entity.getRoleId()),
                entity.getRoleName(),
                entity.getUseYn()
        );
    }
}
