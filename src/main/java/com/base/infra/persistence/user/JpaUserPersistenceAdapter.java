package com.base.infra.persistence.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.domain.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUserPersistenceAdapter implements UserPersistencePort {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.asc("loginId"));

    private final JpaUserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean existsByEmailExceptUserId(String email, Long excludedUserId) {
        return userRepository.existsByEmailAndUserIdNot(email, excludedUserId);
    }

    @Override
    public boolean existsByLoginIdExceptUserId(String loginId, Long excludedUserId) {
        return userRepository.existsByLoginIdAndUserIdNot(loginId, excludedUserId);
    }

    @Override
    public List<User> search(UserSearchCondition condition) {
        UserSearchCondition criteria = condition != null ? condition : new UserSearchCondition();
        criteria.normalize();
        return userRepository.findAll(UserSpecifications.withCondition(criteria), DEFAULT_SORT);
    }
}
