package com.base.application.user.usecase.query.detail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.user.port.in.GetUserUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.query.assembler.UserResultAssembler;
import com.base.application.user.usecase.result.UserResult;
import com.base.domain.user.User;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserResultAssembler userResultAssembler;

    @Override
    public UserResult handle(Long userId) {
        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userResultAssembler.toResult(user);
    }
}
