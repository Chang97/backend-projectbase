package com.application.user;

import org.springframework.stereotype.Service;

import com.infra.mybatis.user.UserMapper;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserMapper userMapper;

    public UserQueryServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
