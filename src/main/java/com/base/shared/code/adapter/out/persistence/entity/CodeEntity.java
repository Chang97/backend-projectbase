package com.base.shared.code.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import com.base.domain.common.BaseEntity;

@Entity
@Table(
    name = "code",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"upper_code_id", "code"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_code_id")
    private CodeEntity upperCode;

    @Column(length = 40, nullable = false, unique = true)
    private String code;

    @Column(length = 200)
    private String codeName;

    @Column(length = 4000)
    private String description;

    private Integer srt;

    @Column(name = "order_path", length = 1000, nullable = false)
    private String orderPath;

    @Column(length = 100)
    private String etc1;

    @Column(length = 100)
    private String etc2;

    @Column(length = 100)
    private String etc3;

    @Column(length = 100)
    private String etc4;
}
