package com.base.contexts.organization.adapter.out.persistence.entity;

import com.base.shared.core.jpa.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "org")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class OrgEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_org_id")
    private OrgEntity upperOrg;

    @Column(name = "org_code", length = 50, nullable = false, unique = true)
    private String orgCode;

    @Column(name = "org_name", length = 200)
    private String orgName;

    private Integer srt;
}
