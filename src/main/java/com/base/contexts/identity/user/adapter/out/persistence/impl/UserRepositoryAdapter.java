package com.base.contexts.identity.user.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.adapter.out.persistence.entity.UserEntity;
import com.base.contexts.identity.user.adapter.out.persistence.mapper.UserEntityMapper;
import com.base.contexts.identity.user.adapter.out.persistence.repo.UserJpaRepository;
import com.base.contexts.identity.user.adapter.out.persistence.spec.UserEntitySpecifications;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.model.UserFilter;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(User user) {
        UserEntity entity;
        if (user.getUserId() == null) {
            entity = mapper.toNewEntity(user);
        } else {
            Long userId = user.getUserId().value();
            entity = jpaRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found: " + userId));
            mapper.merge(user, entity);
        }
        UserEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return jpaRepository.findById(userId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByLoginId(String loginId) {
        return jpaRepository.findByLoginId(loginId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginId(String loginId) {
        return jpaRepository.existsByLoginId(loginId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailExcludingId(String email, Long userId) {
        return jpaRepository.existsByEmailAndUserIdNot(email, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginIdExcludingId(String loginId, Long userId) {
        return jpaRepository.existsByLoginIdAndUserIdNot(loginId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> search(UserFilter filter) {
        return jpaRepository.findAll(UserEntitySpecifications.withFilter(filter)).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
