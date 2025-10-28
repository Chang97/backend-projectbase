package com.base.identity.user.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NotFoundException;
import com.base.identity.user.application.query.dto.UserQueryResult;
import com.base.identity.user.application.query.mapper.UserQueryMapper;
import com.base.identity.user.application.query.port.in.GetUserUseCase;
import com.base.identity.user.domain.port.out.UserRepository;
import com.base.shared.userrolemap.domain.port.out.UserRoleMapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUserHandler implements GetUserUseCase {

    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final UserQueryMapper userQueryMapper;

    @Override
    public UserQueryResult handle(Long userId) {
        return userRepository.findById(userId)
                .map(user -> userQueryMapper.toResult(
                        user,
                        userRoleMapRepository.findRoleIdsByUserId(userId)
                ))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
