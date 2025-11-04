package com.base.contexts.attachment.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileItemEntity;

public interface AtchFileItemJpaRepository extends JpaRepository<AtchFileItemEntity, Long> {

    List<AtchFileItemEntity> findByAtchFile_AtchFileId(Long atchFileId);

    void deleteByAtchFile_AtchFileId(Long atchFileId);
}
