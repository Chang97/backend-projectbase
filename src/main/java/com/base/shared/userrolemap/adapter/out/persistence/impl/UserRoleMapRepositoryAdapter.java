package com.base.shared.userrolemap.adapter.out.persistence.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.shared.userrolemap.adapter.out.persistence.entity.UserRoleMapEntity;
import com.base.shared.userrolemap.adapter.out.persistence.mapper.UserRoleMapEntityMapper;
import com.base.shared.userrolemap.adapter.out.persistence.repo.UserRoleMapJpaRepository;
import com.base.shared.userrolemap.domain.model.UserRoleMap;
import com.base.shared.userrolemap.domain.model.UserRoleMapId;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

@Component
@RequiredArgsConstructor
@Transactional
class UserRoleMapRepositoryAdapter implements UserRoleMapRepository {

    private final UserRoleMapJpaRepository jpaRepository;
    private final UserRoleMapEntityMapper mapper;

    @Override
    public UserRoleMap save(UserRoleMap userRole) {
        UserRoleMapEntity entity = mapper.toEntity(userRole);
        UserRoleMapEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void saveAll(Collection<UserRoleMap> userRoles) {
        List<UserRoleMapEntity> entities = userRoles.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        jpaRepository.saveAll(entities);
    }

    @Override
    public void delete(UserRoleMapId id) {
        jpaRepository.deleteById(mapper.toEntityId(id));
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        jpaRepository.deleteByUserId(userId);
    }
    
    @Override
    public void deleteAllByRoleId(Long roleId) {
        jpaRepository.deleteByRoleId(roleId);
    }

    @Override
    public Optional<UserRoleMap> findById(UserRoleMapId id) {
        return jpaRepository.findById(mapper.toEntityId(id))
                .map(mapper::toDomain);
    }

    @Override
    public List<UserRoleMap> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return jpaRepository.findRoleIdsByUserId(userId);
    }

    @Override
    public List<Long> findUserIdsByRoleIds(Collection<Long> roleIds) {
        return jpaRepository.findUserIdsByRoleIds(roleIds);
    }
}
