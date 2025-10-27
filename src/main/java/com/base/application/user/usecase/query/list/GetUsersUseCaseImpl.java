package com.base.application.user.usecase.query.list;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.user.port.in.GetUsersUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.query.assembler.UserResultAssembler;
import com.base.application.user.usecase.query.condition.UserSearchCondition;
import com.base.application.user.usecase.result.UserResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUsersUseCaseImpl implements GetUsersUseCase {

    private final UserPersistencePort userPersistencePort;
    private final UserResultAssembler userResultAssembler;

    @Override
    public List<UserResult> handle(UserSearchCondition condition) {
        UserSearchCondition criteria = condition != null ? condition : new UserSearchCondition();
        criteria.normalize();

        return userPersistencePort.search(criteria).stream()
                .map(userResultAssembler::toResult)
                .toList();
    }
}
