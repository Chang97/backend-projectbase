package com.base.application.user.query;

import java.util.List;
import java.util.Optional;

import com.base.domain.user.User;

public interface UserQueryService {

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    List<User> getUsers();

    User getUser(Long id);
}
