package com.base.identity.user.application.command.handler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.user.usecase.query.assembler.UserResultAssembler;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;
import com.base.identity.user.application.command.dto.CreateUserCommand;
import com.base.identity.user.application.command.dto.UserResult;
import com.base.identity.user.application.command.mapper.UserCommandMapper;
import com.base.identity.user.application.command.mapper.UserEntityMapper;
import com.base.identity.user.application.port.in.command.CreateUserUseCase;
import com.base.identity.user.application.port.out.query.RoleLookupPort;
import com.base.identity.user.application.port.out.query.UserPersistencePort;
import com.base.identity.user.domain.model.OrgId;
import com.base.identity.user.domain.model.RoleId;
import com.base.identity.user.domain.model.User;
import com.base.identity.user.domain.model.UserRole;
import com.base.identity.user.domain.model.UserStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateUserHandler implements CreateUserUseCase {

    private final UserPersistencePort userPersistencePort;
    // private final UserEntityMapper userEntityMapper;
    // private final UserCommandSupport userCommandSupport;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;
    private final UserResultAssembler userResultAssembler;
    private final UserCommandMapper userCommandMapper;
    private final RoleLookupPort roleLookupPort;

    @Override
    public UserResult handle(CreateUserCommand command) {
        validateUniqueness(command);
        User user = userCommandMapper.toDomain(
            command.email(),
            command.rawPassword(),
            new UserStatus(null, null) command.userStatusCodeId(),
            new OrgId(command.orgId())
            // new command.roleIds()
        );

        try {
            User saved = userPersistencePort.save(user);
            cacheInvalidationEventPublisher.publishRoleAuthorityChanged(List.of(saved.getUserId()));
            return userResultAssembler.toResult(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ValidationException(resolveDataIntegrityMessage(ex));
        }
    }

    private void validateUniqueness(CreateUserCommand command) {
        if (StringUtils.hasText(command.email()) && userPersistencePort.existsByEmail(command.email())) {
            throw new ConflictException("Email already exists: " + command.email());
        }
        if (StringUtils.hasText(command.loginId()) && userPersistencePort.existsByLoginId(command.loginId())) {
            throw new ConflictException("LoginId already exists: " + command.loginId());
        }
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
