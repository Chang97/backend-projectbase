package com.base.contexts.identity.user.application.command.handler;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.application.command.dto.UserPasswordCommand;
import com.base.contexts.identity.user.application.command.port.in.ChangePasswordUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserCommandPort;
import com.base.platform.exception.NotFoundException;
import com.base.platform.exception.ValidationException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class ChangePasswordHandler implements ChangePasswordUseCase {

    private final UserCommandPort userCommandPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void handle(UserPasswordCommand command) {
        User user = userCommandPort.findById(command.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String normalized = StringNormalizer.trimToNull(command.rawPassword());
        if (normalized == null) {
            throw new ValidationException("비밀번호는 필수값입니다.");
        }

        user.changePassword(passwordEncoder.encode(normalized), LocalDateTime.now());
        userCommandPort.save(user);
    }
}
