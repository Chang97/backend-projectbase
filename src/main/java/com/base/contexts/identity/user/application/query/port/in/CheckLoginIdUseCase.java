package com.base.contexts.identity.user.application.query.port.in;

import com.base.contexts.identity.user.application.query.dto.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
