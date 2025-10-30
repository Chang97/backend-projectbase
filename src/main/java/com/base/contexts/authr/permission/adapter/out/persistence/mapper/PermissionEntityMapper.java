package com.base.contexts.authr.permission.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.permission.adapter.out.persistence.entity.PermissionEntity;
import com.base.contexts.authr.permission.domain.model.Permission;
import com.base.contexts.authr.permission.domain.model.PermissionId;

@Component
public class PermissionEntityMapper {

    public PermissionEntity toNewEntity(Permission domain) {
        PermissionEntity entity = new PermissionEntity();
        apply(domain, entity);
        return entity;
    }

    public void merge(Permission domain, PermissionEntity target) {
        // 감사필드(createdId 등)는 target에 이미 들어 있으므로 건드리지 않습니다.
        apply(domain, target);
    }

    private void apply(Permission domain, PermissionEntity target) {
        target.setPermissionCode(domain.getPermissionCode());
        target.setPermissionName(domain.getPermissionName());
        target.setPermissionName(domain.getPermissionName());
        target.setUseYn(Boolean.TRUE.equals(domain.getUseYn()));
    }

    public Permission toDomain(PermissionEntity entity) {
        return Permission.restore(
            PermissionId.of(entity.getPermissionId()),
            entity.getPermissionCode(),
            entity.getPermissionName(),
            entity.getUseYn()
        );
  }
}
