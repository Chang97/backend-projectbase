package com.base.application.user.port.in;

import com.base.application.user.usecase.result.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
