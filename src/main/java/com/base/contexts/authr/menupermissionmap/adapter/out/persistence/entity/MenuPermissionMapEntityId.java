package com.base.contexts.authr.menupermissionmap.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class MenuPermissionMapEntityId implements Serializable {

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "permission_id")
    private Long permissionId;

    public MenuPermissionMapEntityId(Long menuId, Long permissionId) {
        this.menuId = menuId;
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuPermissionMapEntityId that)) {
            return false;
        }
        return Objects.equals(menuId, that.menuId)
                && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, permissionId);
    }
}
