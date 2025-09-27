package com.base.domain.file;

import com.base.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "atch_file_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtchFileItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atch_file_item_id")
    private Long atchFileItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atch_file_id", nullable = false)
    private AtchFile atchFile;

    @Column(length = 400)
    private String path;

    @Column(length = 400)
    private String fileName;

    private Integer fileSize;
}
