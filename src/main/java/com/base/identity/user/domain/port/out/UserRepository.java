package com.base.identity.user.domain.port.out;

import java.util.Optional;

import com.base.identity.user.domain.model.User;
import com.base.identity.user.domain.model.UserId;

public interface UserRepository {
    boolean existsByEmail(String email);
    boolean existsByLoginId(String loginId);
    Optional<User> findById(UserId userId);
    UserId save(User user);
}
