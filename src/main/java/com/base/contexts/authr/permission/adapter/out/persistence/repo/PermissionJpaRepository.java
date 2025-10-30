package com.base.contexts.authr.permission.adapter.out.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.contexts.authr.permission.adapter.out.persistence.entity.PermissionEntity;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long>, JpaSpecificationExecutor<PermissionEntity> {
    Optional<PermissionEntity> findByPermissionCode(String permissionCode);
    boolean existsByPermissionCode(String permissionCode);
}
