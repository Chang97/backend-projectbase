package com.base.contexts.identity.user.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.authr.userrolemap.domain.port.out.UserRoleMapRepository;
import com.base.contexts.identity.user.application.query.dto.UserQuery;
import com.base.contexts.identity.user.application.query.dto.UserQueryResult;
import com.base.contexts.identity.user.application.query.mapper.UserQueryMapper;
import com.base.contexts.identity.user.application.query.port.in.GetUsersUseCase;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.model.UserFilter;
import com.base.contexts.identity.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUsersHandler implements GetUsersUseCase {

    private final UserRepository userRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final UserQueryMapper userQueryMapper;

    @Override
    public List<UserQueryResult> handle(UserQuery query) {
        UserFilter filter = userQueryMapper.toFilter(query);
        List<User> users = userRepository.search(filter);
        if (users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(user -> userQueryMapper.toResult(
                        user,
                        user.getUserId() != null
                                ? userRoleMapRepository.findRoleIdsByUserId(user.getUserId().value())
                                : List.of()
                ))
                .toList();
    }
}
