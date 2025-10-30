package com.base.contexts.identity.user.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.model.UserFilter;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmailExcludingId(String email, Long userId);

    boolean existsByLoginIdExcludingId(String loginId, Long userId);

    List<User> search(UserFilter filter);
}
