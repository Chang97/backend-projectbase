package com.base.identity.user.adapter.out.persistence.repo;

import org.springframework.stereotype.Component;

import com.base.identity.user.adapter.out.persistence.entity.UserEntity;
import com.base.identity.user.adapter.out.persistence.mapper.UserEntityMapper;
import com.base.identity.user.domain.model.User;
import com.base.identity.user.domain.model.UserId;
import com.base.identity.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository repository;
    private final UserEntityMapper mapper;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserId save(User user) {
        UserEntity saved = repository.save(mapper.toEntity(user));
        return UserId.of(saved.getUserId());
    }

    // 기타 구현...
}
