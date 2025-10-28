package com.base.shared.rolepermissionmap.adapter.out.persistence.entity;

import com.base.domain.common.BaseEntity;
import com.base.shared.permission.adapter.out.persistence.entity.PermissionEntity;

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
@Table(name = "role_permission_map")
@Getter
@Setter
@NoArgsConstructor
public class RolePermissionMapEntity extends BaseEntity {

    @EmbeddedId
    private RolePermissionMapEntityId id = new RolePermissionMapEntityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private com.base.domain.role.Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    @Column(name = "permission_id", insertable = false, updatable = false)
    private Long permissionId;
}
