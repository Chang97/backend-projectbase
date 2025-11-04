package com.base.domain.common;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    
    @Builder.Default
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn = true;

    @CreatedBy
    @Column(name = "created_id", updatable = false)
    private Long createdId;

    @CreatedDate
    @Column(name = "created_dt", columnDefinition = "timestamptz default now()", updatable = false)
    private OffsetDateTime createdDt;

    @LastModifiedBy
    @Column(name = "updated_id")
    private Long updatedId;

    @LastModifiedDate
    @Column(name = "updated_dt", columnDefinition = "timestamptz default now()")
    private OffsetDateTime updatedDt;
}
