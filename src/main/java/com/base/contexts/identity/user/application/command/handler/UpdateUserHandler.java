package com.base.contexts.identity.user.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.application.command.dto.UserCommand;
import com.base.contexts.identity.user.application.command.dto.UserCommandResult;
import com.base.contexts.identity.user.application.command.mapper.UserCommandMapper;
import com.base.contexts.identity.user.application.command.port.in.UpdateUserUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.contexts.identity.user.domain.port.out.UserRoleAssignmentPort;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateUserHandler implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final UserCommandMapper userCommandMapper;
    private final UserRoleAssignmentPort userRoleAssignmentPort;

    @Override
    public UserCommandResult handle(Long userId, UserCommand command) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        validateUniqueness(userId, command);
        userCommandMapper.apply(existing, command);
        User saved = userRepository.save(existing);
        // 사용자 역할 등록/삭제
        userRoleAssignmentPort.replaceUserRoles(saved.getUserId().value(), command.roleIds());
        return new UserCommandResult(saved.getUserId().value());
    }

    private void validateUniqueness(Long userId, UserCommand command) {
        String email = StringNormalizer.trimToNull(command.email());
        if (email != null && userRepository.existsByEmailExcludingId(email, userId)) {
            throw new ConflictException("이미 사용 중인 이메일입니다.");
        }
        String loginId = StringNormalizer.trimToNull(command.loginId());
        if (loginId != null && userRepository.existsByLoginIdExcludingId(loginId, userId)) {
            throw new ConflictException("이미 사용 중인 로그인 ID입니다.");
        }
    }


}
