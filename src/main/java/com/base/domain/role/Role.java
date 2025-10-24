package com.base.domain.role;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import com.base.domain.common.BaseEntity;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", length = 200, nullable = false, unique = true)
    private String roleName;

}