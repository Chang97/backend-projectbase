package com.base.domain.role;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", length = 200, nullable = false, unique = true)
    private String roleName;

    @Builder.Default
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn = true;

    @Column(name = "created_id")
    private Integer createdId;

    @Column(name = "created_dt", columnDefinition = "timestamptz default now()")
    private OffsetDateTime createdDt;

    @Column(name = "updated_id")
    private Integer updatedId;

    @Column(name = "updated_dt", columnDefinition = "timestamptz default now()")
    private OffsetDateTime updatedDt;
}