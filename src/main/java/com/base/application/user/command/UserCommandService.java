package com.base.application.user.command;

import com.base.api.user.dto.PasswordChangeRequest;
import com.base.api.user.dto.UserRequest;
import com.base.api.user.dto.UserResponse;

public interface UserCommandService {

    UserResponse createUser(UserRequest user);

    UserResponse updateUser(Long id, UserRequest user);

    void deleteUser(Long id);

    void changePassword(Long id, PasswordChangeRequest request);
}
