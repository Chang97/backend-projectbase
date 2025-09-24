package com.application.user;

import com.api.user.dto.UserResponse;

public interface UserCommandService {

    UserResponse findById(Long id);

    
}
