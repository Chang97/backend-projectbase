package com.base.contexts.authr.menupermissionmap.adapter.out.persistence.entity;

import com.base.contexts.authr.menu.adapter.out.persistence.entity.MenuEntity;
import com.base.contexts.authr.permission.adapter.out.persistence.entity.PermissionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu_permission_map")
@Getter
@Setter
@NoArgsConstructor
public class MenuPermissionMapEntity {

    @EmbeddedId
    private MenuPermissionMapEntityId id = new MenuPermissionMapEntityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuId")
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Long menuId;

    @Column(name = "permission_id", insertable = false, updatable = false)
    private Long permissionId;
}
