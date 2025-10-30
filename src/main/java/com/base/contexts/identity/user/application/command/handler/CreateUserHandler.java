package com.base.contexts.identity.user.application.command.handler;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.role.domain.model.Role;
import com.base.contexts.authr.role.domain.port.out.RoleRepository;
import com.base.contexts.authr.userrolemap.domain.model.UserRoleMap;
import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapRepository;
import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.application.command.dto.UserCommandResult;
import com.base.contexts.identity.user.application.command.mapper.UserCommandMapper;
import com.base.contexts.identity.user.application.command.port.in.CreateUserUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateUserHandler implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final UserCommandMapper userCommandMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserCommandResult handle(UserCommand command) {
        validateUniqueness(command);
        String encodedPassword = encodePassword(command.rawPassword());
        User user = userCommandMapper.toDomain(command, encodedPassword);
        User saved = userRepository.save(user);
        syncRoles(saved.getUserId().value(), command.roleIds());
        authorityCacheEventPort.publishRoleAuthoritiesChanged(List.of(saved.getUserId().value()));
        return new UserCommandResult(saved.getUserId().value());
    }

    private void validateUniqueness(UserCommand command) {
        String email = StringNormalizer.trimToNull(command.email());
        if (email != null && userRepository.existsByEmail(email)) {
            throw new ConflictException("이미 사용 중인 이메일입니다.");
        }
        String loginId = StringNormalizer.trimToNull(command.loginId());
        if (loginId != null && userRepository.existsByLoginId(loginId)) {
            throw new ConflictException("이미 사용 중인 로그인 ID입니다.");
        }
    }

    private String encodePassword(String rawPassword) {
        String normalized = StringNormalizer.trimToNull(rawPassword);
        if (normalized == null) {
            throw new ValidationException("비밀번호는 필수값입니다.");
        }
        return passwordEncoder.encode(normalized);
    }

    private void syncRoles(Long userId, List<Long> roleIds) {
        List<Long> sanitized = sanitizeRoleIds(roleIds);
        userRoleMapRepository.deleteAllByUserId(userId);
        if (sanitized.isEmpty()) {
            return;
        }
        validateRoleExistence(sanitized);
        List<UserRoleMap> mappings = sanitized.stream()
                .map(roleId -> UserRoleMap.of(userId, roleId))
                .toList();
        userRoleMapRepository.saveAll(mappings);
    }

    private List<Long> sanitizeRoleIds(List<Long> roleIds) {
        if (roleIds == null) {
            return Collections.emptyList();
        }
        Set<Long> distinct = roleIds.stream()
                .filter(Objects::nonNull)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
        return List.copyOf(distinct);
    }

    private void validateRoleExistence(List<Long> roleIds) {
        List<Role> roles = roleRepository.findAllByIds(roleIds);
        Set<Long> existing = roles.stream()
                .map(role -> role.getRoleId() != null ? role.getRoleId().value() : null)
                .filter(Objects::nonNull)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
        if (existing.size() != roleIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }
    }
}
