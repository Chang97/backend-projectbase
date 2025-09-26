package com.base.application.user.command;

import com.base.domain.user.User;

public interface UserCommandService {

    User createUser(User user);

    User updateUser(Long id, User user);
}
