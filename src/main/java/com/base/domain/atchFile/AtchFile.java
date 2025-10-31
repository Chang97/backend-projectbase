package com.base.domain.atchFile;

import com.base.contexts.code.adapter.out.persistence.entity.CodeEntity;
import com.base.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "atch_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtchFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atch_file_id")
    private Long atchFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_grp_code_id")
    private CodeEntity fileGrpCode;
}
