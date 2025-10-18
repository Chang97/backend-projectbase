package com.base.application.user.command;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.base.api.user.dto.PasswordChangeRequest;
import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.domain.code.Code;
import com.base.domain.mapping.UserRoleMap;
import com.base.domain.org.Org;
import com.base.domain.role.Role;
import com.base.domain.role.RoleRepository;
import com.base.domain.user.User;
import com.base.domain.user.UserRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
        syncUserRoles(user, request.roleIds());
        try {
            User saved = userRepository.save(user);
            return userMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ValidationException(resolveDataIntegrityMessage(ex));
        }
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (StringUtils.hasText(request.email())
                && userRepository.existsByEmailAndUserIdNot(request.email(), existing.getUserId())) {
            throw new ConflictException("Email already exists: " + request.email());
        }
        if (StringUtils.hasText(request.loginId())
                && userRepository.existsByLoginIdAndUserIdNot(request.loginId(), existing.getUserId())) {
            throw new ConflictException("LoginId already exists: " + request.loginId());
        }

        userMapper.updateFromRequest(request, existing);
        applyReferences(existing, request);
        applyPassword(existing, request.userPassword());
        syncUserRoles(existing, request.roleIds());
        try {
            User saved = userRepository.save(existing);
            return userMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ValidationException(resolveDataIntegrityMessage(ex));
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        existing.setUseYn(false); // soft delete
        userRepository.save(existing);
    }

    @Override
    @Transactional
    public void changePassword(Long id, PasswordChangeRequest request) {
        if (request == null || !StringUtils.hasText(request.newPassword())) {
            throw new ValidationException("새 비밀번호를 입력해주세요.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        String storedPassword = user.getUserPassword();
        if (StringUtils.hasText(storedPassword)) {
            // if (!StringUtils.hasText(request.currentPassword())
            //         || !passwordEncoder.matches(request.currentPassword(), storedPassword)) {
            //     throw new ValidationException("현재 비밀번호가 일치하지 않습니다.");
            // }
            // if (passwordEncoder.matches(request.newPassword(), storedPassword)) {
            //     throw new ValidationException("새 비밀번호가 이전 비밀번호와 동일합니다.");
            // }
        }

        user.setOld1UserPassword(storedPassword);
        user.setUserPassword(passwordEncoder.encode(request.newPassword()));
        user.setUserPasswordUpdateDt(OffsetDateTime.now());
        user.setUserPasswordFailCnt(0);

        userRepository.save(user);
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

        Set<UserRoleMap> currentMappings = user.getRoles();

        if (CollectionUtils.isEmpty(roleIds)) {
            currentMappings.clear();
            return;
        }

        Set<Long> distinctRoleIds = roleIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        currentMappings.removeIf(mapping -> {
            Role role = mapping.getRole();
            Long roleId = role != null ? role.getRoleId() : null;
            return roleId == null || !distinctRoleIds.contains(roleId);
        });

        Set<Long> existingRoleIds = currentMappings.stream()
                .map(UserRoleMap::getRole)
                .filter(Objects::nonNull)
                .map(Role::getRoleId)
                .collect(Collectors.toSet());

        Set<Long> missingRoleIds = distinctRoleIds.stream()
                .filter(roleId -> !existingRoleIds.contains(roleId))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (missingRoleIds.isEmpty()) {
            return;
        }

        List<Role> rolesToAdd = roleRepository.findAllById(missingRoleIds);
        if (rolesToAdd.size() != missingRoleIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        Map<Long, Role> roleMapById = rolesToAdd.stream()
                .collect(Collectors.toMap(Role::getRoleId, r -> r));

        missingRoleIds.forEach(roleId -> currentMappings.add(UserRoleMap.builder()
                .user(user)
                .role(roleMapById.get(roleId))
                .build()));
    }

    private String resolveDataIntegrityMessage(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        if (message != null && message.toLowerCase().contains("email")) {
            return "이메일은 필수값입니다.";
        }
        if (message != null && message.toLowerCase().contains("login_id")) {
            return "로그인 ID가 유효하지 않습니다.";
        }
        return "데이터 무결성 오류가 발생했습니다.";
    }
}
