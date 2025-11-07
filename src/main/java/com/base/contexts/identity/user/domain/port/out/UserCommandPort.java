package com.base.contexts.identity.user.domain.port.out;

import java.util.Optional;

import com.base.contexts.identity.user.domain.model.User;

public interface UserCommandPort {

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmailExcludingId(String email, Long userId);

    boolean existsByLoginIdExcludingId(String loginId, Long userId);
}
