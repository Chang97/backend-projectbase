package com.base.application.user.query;

import java.util.List;

import com.base.api.user.dto.UserResponse;

public interface UserQueryService {

    UserResponse getUser(Long id);
    List<UserResponse> getUsers();
}
