package com.base.application.user.command;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.base.domain.user.User;
import com.base.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    // private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    
}
