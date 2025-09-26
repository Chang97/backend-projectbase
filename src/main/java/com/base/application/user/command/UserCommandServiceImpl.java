package com.base.application.user.command;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.base.domain.user.User;
import com.base.domain.user.UserRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!existing.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already exists: " + user.getEmail());
        }

        existing.setUserName(user.getUserName());
        existing.setLoginId(user.getLoginId());
        return userRepository.save(existing);
    }
    
}
