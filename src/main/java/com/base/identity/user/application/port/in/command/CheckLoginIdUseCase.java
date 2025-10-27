package com.base.identity.user.application.port.in.command;

import com.base.application.user.usecase.result.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
