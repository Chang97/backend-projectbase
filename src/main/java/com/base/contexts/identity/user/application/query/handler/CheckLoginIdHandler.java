package com.base.contexts.identity.user.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.identity.user.application.query.dto.LoginIdAvailabilityResult;
import com.base.contexts.identity.user.application.query.mapper.UserQueryMapper;
import com.base.contexts.identity.user.application.query.port.in.CheckLoginIdUseCase;
import com.base.contexts.identity.user.domain.port.out.UserQueryPort;
import com.base.platform.exception.ValidationException;
import com.base.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CheckLoginIdHandler implements CheckLoginIdUseCase {

    private final UserQueryPort userQueryPort;
    private final UserQueryMapper userQueryMapper;

    @Override
    public LoginIdAvailabilityResult handle(String loginId) {
        String normalized = StringNormalizer.trimToNull(loginId);
        if (normalized == null) {
            throw new ValidationException("로그인 ID는 필수값입니다.");
        }
        boolean available = !userQueryPort.existsByLoginId(normalized);
        return userQueryMapper.toLoginIdAvailability(normalized, available);
    }
}
