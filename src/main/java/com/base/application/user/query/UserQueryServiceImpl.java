package com.base.application.user.query;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.domain.user.UserRepository;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(UserSearchCondition condition) {
        UserSearchCondition criteria = condition != null ? condition : new UserSearchCondition();
        criteria.normalize();
        Sort sort = Sort.by(Sort.Order.asc("loginId"));
        return userRepository.findAll(UserSpecifications.withCondition(criteria), sort).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLoginIdAvailable(String loginId) {
        if (!StringUtils.hasText(loginId)) {
            throw new ValidationException("LoginId must not be empty");
        }
        return !userRepository.existsByLoginId(loginId);
    }

}
