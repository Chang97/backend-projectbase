package com.base.application.user.command;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.domain.code.Code;
import com.base.domain.org.Org;
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
    private final UserMapper userMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists: " + request.email());
        }
        if (request.loginId() != null && userRepository.existsByLoginId(request.loginId())) {
            throw new ConflictException("LoginId already exists: " + request.loginId());
        }
        User user = userMapper.toEntity(request);
        applyReferences(user, request);
        applyPassword(user, request.userPassword());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!existing.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists: " + request.email());
        }
        if (request.loginId() != null
                && !request.loginId().equals(existing.getLoginId())
                && userRepository.existsByLoginId(request.loginId())) {
            throw new ConflictException("LoginId already exists: " + request.loginId());
        }

        userMapper.updateFromRequest(request, existing);
        applyReferences(existing, request);
        applyPassword(existing, request.userPassword());
        return userMapper.toResponse(userRepository.save(existing));
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
    
}

