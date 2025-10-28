package com.base.shared.permission.adapter.out.persistence.entity;

import com.base.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_code", length = 100, nullable = false, unique = true)
    private String permissionCode;

    @Column(name = "permission_name", length = 200)
    private String permissionName;

}
