package com.base.contexts.identity.user.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapQueryPort;
import com.base.contexts.identity.user.application.query.dto.UserQueryResult;
import com.base.contexts.identity.user.application.query.mapper.UserQueryMapper;
import com.base.contexts.identity.user.application.query.port.in.GetUserUseCase;
import com.base.contexts.identity.user.domain.port.out.UserQueryPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUserHandler implements GetUserUseCase {

    private final UserQueryPort userQueryPort;
    private final UserRoleMapQueryPort userRoleMapQueryPort;
    private final UserQueryMapper userQueryMapper;

    @Override
    public UserQueryResult handle(Long userId) {
        return userQueryPort.findById(userId)
                .map(user -> userQueryMapper.toResult(
                        user,
                        userRoleMapQueryPort.findRoleIdsByUserId(userId)
                ))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
