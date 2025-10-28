package com.base.identity.user.application.query.port.in;

import com.base.identity.user.application.query.dto.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
