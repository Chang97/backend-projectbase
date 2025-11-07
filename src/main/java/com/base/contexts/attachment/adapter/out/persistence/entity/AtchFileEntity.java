package com.base.contexts.attachment.adapter.out.persistence.entity;

import com.base.shared.core.jpa.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "atch_file")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AtchFileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atch_file_id")
    private Long atchFileId;

    @Column(name = "file_grp_code_id")
    private Long fileGroupCodeId;
}
