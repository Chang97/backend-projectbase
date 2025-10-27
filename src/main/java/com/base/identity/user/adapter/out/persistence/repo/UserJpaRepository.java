package com.base.identity.user.adapter.out.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.domain.user.User;

public interface UserJpaRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmailAndUserIdNot(String email, Long userId);

    boolean existsByLoginIdAndUserIdNot(String loginId, Long userId);
}
