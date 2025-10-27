package com.base.identity.user.application.command.handler;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.user.port.in.UpdateUserUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.command.UpdateUserCommand;
import com.base.application.user.usecase.create.UserEntityMapper;
import com.base.application.user.usecase.result.UserResult;
import com.base.application.user.usecase.support.UserCommandSupport;
import com.base.application.user.usecase.query.assembler.UserResultAssembler;
import com.base.domain.user.User;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateUserHandler implements UpdateUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserEntityMapper userEntityMapper;
    private final UserCommandSupport userCommandSupport;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;
    private final UserResultAssembler userResultAssembler;

    @Override
    public UserResult handle(Long userId, UpdateUserCommand command) {
        User existing = userPersistencePort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        validateUniqueness(userId, command);

        userEntityMapper.applyUpdates(existing, command);
        userCommandSupport.applyReferences(existing, command.orgId(), command.userStatusId());
        userCommandSupport.applyPassword(existing, command.rawPassword());
        userCommandSupport.syncUserRoles(existing, command.roleIds());

        try {
            User saved = userPersistencePort.save(existing);
            cacheInvalidationEventPublisher.publishRoleAuthorityChanged(List.of(saved.getUserId()));
            return userResultAssembler.toResult(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ValidationException(resolveDataIntegrityMessage(ex));
        }
    }

    private void validateUniqueness(Long userId, UpdateUserCommand command) {
        if (StringUtils.hasText(command.email())
                && userPersistencePort.existsByEmailExceptUserId(command.email(), userId)) {
            throw new ConflictException("Email already exists: " + command.email());
        }
        if (StringUtils.hasText(command.loginId())
                && userPersistencePort.existsByLoginIdExceptUserId(command.loginId(), userId)) {
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
