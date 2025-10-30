package com.base.contexts.authr.userrolemap.adapter.out.persistence.entity;

import com.base.contexts.authr.role.adapter.out.persistence.entity.RoleEntity;
import com.base.contexts.identity.user.adapter.out.persistence.entity.UserEntity;

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
@Table(name = "user_role_map")
@Getter
@Setter
@NoArgsConstructor
public class UserRoleMapEntity {

    @EmbeddedId
    private UserRoleMapEntityId id = new UserRoleMapEntityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;
}
