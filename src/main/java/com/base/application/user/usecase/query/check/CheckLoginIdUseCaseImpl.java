package com.base.application.user.usecase.query.check;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.application.user.port.in.CheckLoginIdUseCase;
import com.base.application.user.port.out.UserPersistencePort;
import com.base.application.user.usecase.result.LoginIdAvailabilityResult;
import com.base.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CheckLoginIdUseCaseImpl implements CheckLoginIdUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public LoginIdAvailabilityResult handle(String loginId) {
        if (!StringUtils.hasText(loginId)) {
            throw new ValidationException("LoginId must not be empty");
        }
        boolean available = !userPersistencePort.existsByLoginId(loginId);
        return new LoginIdAvailabilityResult(available);
    }
}
