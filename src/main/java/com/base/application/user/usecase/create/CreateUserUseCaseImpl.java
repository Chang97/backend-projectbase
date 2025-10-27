package com.base.application.user.usecase.create;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.user.port.in.CreateUserUseCase;
import com.base.application.user.usecase.command.CreateUserCommand;
import com.base.application.user.usecase.result.UserResult;
import com.base.application.user.usecase.support.UserCommandSupport;
import com.base.application.user.usecase.query.assembler.UserResultAssembler;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.domain.user.User;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserEntityMapper userEntityMapper;
    private final UserCommandSupport userCommandSupport;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;
    private final UserResultAssembler userResultAssembler;

    @Override
    public UserResult handle(CreateUserCommand command) {
        validateUniqueness(command);

        User user = userEntityMapper.toEntity(command);
        userCommandSupport.applyReferences(user, command.orgId(), command.userStatusId());
        userCommandSupport.applyPassword(user, command.rawPassword());
        userCommandSupport.syncUserRoles(user, command.roleIds());

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
