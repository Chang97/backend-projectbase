package com.base.contexts.identity.user.application.command.handler;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.application.command.dto.UserCommandResult;
import com.base.contexts.identity.user.application.command.mapper.UserCommandMapper;
import com.base.contexts.identity.user.application.command.port.in.CreateUserUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.contexts.identity.user.domain.port.out.UserRoleAssignmentPort;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.ValidationException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateUserHandler implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final UserCommandMapper userCommandMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleAssignmentPort userRoleAssignmentPort;

    @Override
    public UserCommandResult handle(UserCommand command) {
        validateUniqueness(command);
        String encodedPassword = encodePassword(command.rawPassword());
        User user = userCommandMapper.toDomain(command, encodedPassword);
        User saved = userRepository.save(user);
        userRoleAssignmentPort.replaceUserRoles(saved.getUserId().value(), command.roleIds());
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
}
