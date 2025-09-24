package com.application.user;

import org.springframework.stereotype.Service;

import com.api.user.dto.UserResponse;
import com.infra.mybatis.user.UserMapper;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserMapper userMapper;

    public UserCommandServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Override
    public UserResponse findById(Long id) {
        return userMapper.findById(id);
        
    }

    
}
