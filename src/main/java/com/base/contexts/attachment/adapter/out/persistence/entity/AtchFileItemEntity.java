package com.base.contexts.attachment.adapter.out.persistence.entity;

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
@Table(name = "atch_file_item")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AtchFileItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atch_file_item_id")
    private Long atchFileItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atch_file_id", nullable = false)
    private AtchFileEntity atchFile;

    @Column(length = 400)
    private String path;

    @Column(length = 400)
    private String fileName;

    private Long fileSize;
}
