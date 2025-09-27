package com.base.domain.org;

import com.base.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "org")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Org extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_org_id")
    private Org upperOrg;

    @Column(name = "org_code", length = 50, nullable = false, unique = true)
    private String orgCode;

    @Column(name = "org_name", length = 200)
    private String orgName;

    private Integer srt;
}
