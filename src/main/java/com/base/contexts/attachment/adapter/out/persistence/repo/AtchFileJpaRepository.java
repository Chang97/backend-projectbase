package com.base.contexts.attachment.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.contexts.attachment.adapter.out.persistence.entity.AtchFileEntity;

public interface AtchFileJpaRepository extends JpaRepository<AtchFileEntity, Long> {
}
