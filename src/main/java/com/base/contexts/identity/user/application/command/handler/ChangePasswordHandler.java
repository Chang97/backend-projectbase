package com.base.contexts.identity.user.application.command.handler;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.application.command.dto.UserPasswordCommand;
import com.base.contexts.identity.user.application.command.port.in.ChangePasswordUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class ChangePasswordHandler implements ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void handle(UserPasswordCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        String normalized = StringNormalizer.trimToNull(command.rawPassword());
        if (normalized == null) {
            throw new ValidationException("비밀번호는 필수값입니다.");
        }

        user.changePassword(passwordEncoder.encode(normalized), OffsetDateTime.now());
        userRepository.save(user);
    }
}
