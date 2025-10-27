package com.base.infra.persistence.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.domain.role.Role;

public interface JpaRoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);
}
