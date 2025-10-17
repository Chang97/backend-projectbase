package com.base.application.user.command;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.domain.code.Code;
import com.base.domain.mapping.UserRoleMap;
import com.base.domain.mapping.UserRoleMapRepository;
import com.base.domain.org.Org;
import com.base.domain.role.Role;
import com.base.domain.user.User;
import com.base.domain.user.UserRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final UserMapper userMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists: " + request.email());
        }
        if (StringUtils.hasText(request.loginId()) && userRepository.existsByLoginId(request.loginId())) {
            throw new ConflictException("LoginId already exists: " + request.loginId());
        }
        User user = userMapper.toEntity(request);
        applyReferences(user, request);
        applyPassword(user, request.userPassword());
        User saved = userRepository.save(user);
        syncUserRoles(saved, request.roleIds());
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!Objects.equals(existing.getEmail(), request.email())
                && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists: " + request.email());
        }
        if (StringUtils.hasText(request.loginId())
                && !Objects.equals(request.loginId(), existing.getLoginId())
                && userRepository.existsByLoginId(request.loginId())) {
            throw new ConflictException("LoginId already exists: " + request.loginId());
        }

        userMapper.updateFromRequest(request, existing);
        applyReferences(existing, request);
        applyPassword(existing, request.userPassword());
        User saved = userRepository.save(existing);
        syncUserRoles(saved, request.roleIds());
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        existing.setUseYn(false); // soft delete
        userRepository.save(existing);
    }

    private void applyReferences(User user, UserRequest request) {
        if (request.orgId() != null) {
            user.setOrg(entityManager.getReference(Org.class, request.orgId()));
        } else {
            user.setOrg(null);
        }
        if (request.userStatusId() != null) {
            user.setUserStatus(entityManager.getReference(Code.class, request.userStatusId()));
        } else {
            user.setUserStatus(null);
        }
    }

    private void applyPassword(User user, String rawPassword) {
        if (StringUtils.hasText(rawPassword)) {
            user.setUserPassword(passwordEncoder.encode(rawPassword));
        }
    }

    private void syncUserRoles(User user, List<Long> roleIds) {
        if (user.getRoles() == null) {
            user.setRoles(new LinkedHashSet<>());
        }

        userRoleMapRepository.deleteByUser(user);
        user.getRoles().clear();

        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        Set<Long> distinctRoleIds = roleIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<UserRoleMap> newMappings = distinctRoleIds.stream()
                .map(roleId -> UserRoleMap.builder()
                        .user(user)
                        .role(entityManager.getReference(Role.class, roleId))
                        .build())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        newMappings.forEach(userRoleMapRepository::save);
        user.getRoles().addAll(newMappings);
    }
}
