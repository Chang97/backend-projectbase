package com.base.identity.user.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.application.user.port.in.DeleteUserUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.domain.user.User;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteUserHandler implements DeleteUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;

    @Override
    public void handle(Long userId) {
        User existing = userPersistencePort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        existing.setUseYn(false);
        userPersistencePort.save(existing);
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(List.of(existing.getUserId()));
    }
}
