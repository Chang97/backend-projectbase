package com.base.application.user.port.out;

import java.util.List;
import java.util.Optional;

import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.domain.user.User;

/**
 * 사용자 저장소 접근을 추상화한 아웃바운드 포트.
 */
public interface UserPersistencePort {

    User save(User user);

    void delete(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmailExceptUserId(String email, Long excludedUserId);

    boolean existsByLoginIdExceptUserId(String loginId, Long excludedUserId);

    List<User> search(UserSearchCondition condition);
}
