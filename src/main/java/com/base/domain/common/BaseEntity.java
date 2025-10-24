package com.base.domain.common;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn = true;

    @Column(name = "created_id")
    private Long createdId;

    @Column(name = "created_dt", columnDefinition = "timestamptz default now()")
    private OffsetDateTime createdDt = OffsetDateTime.now();

    @Column(name = "updated_id")
    private Long updatedId;

    @Column(name = "updated_dt", columnDefinition = "timestamptz default now()")
    private OffsetDateTime updatedDt = OffsetDateTime.now();
}
