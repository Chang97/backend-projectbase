package com.base.domain.code;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
    name = "code",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"upper_code_id", "code"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId; // 코드 PK

    // 자기 자신 참조 (상위 코드)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_code_id")
    private Code upperCode;

    @Column(name = "code", length = 40, nullable = false, unique = true)
    private String code; // 코드 값

    @Column(name = "code_name", length = 200)
    private String codeName; // 코드명

    @Column(length = 4000)
    private String description; // 코드 설명

    @Column
    private Integer srt; // 정렬 순서

    @Column(length = 100)
    private String etc1;

    @Column(length = 100)
    private String etc2;

    @Column(length = 100)
    private String etc3;

    @Column(length = 100)
    private String etc4;

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
