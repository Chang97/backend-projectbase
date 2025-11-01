package com.base.contexts.identity.user.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.application.command.port.in.DeleteUserUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteUserHandler implements DeleteUserUseCase {

    private final UserRepository userRepository;

    @Override
    public void handle(Long userId) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        existing.disable();
        userRepository.save(existing);
    }
}
