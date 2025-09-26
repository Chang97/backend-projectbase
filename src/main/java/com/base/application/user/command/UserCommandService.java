package com.base.application.user.command;

import java.util.List;
import java.util.Optional;

import com.base.domain.user.User;

public interface UserCommandService {
    User saveUser(User user);
}
