package com.base.authn.application.usecase.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.authn.application.usecase.result.AuthSession;
import com.base.authn.application.usecase.support.AuthSupport;
import com.base.exception.ValidationException;
import com.base.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * 현재 SecurityContext에 저장된 인증 정보를 기반으로
 * 사용자 세션 스냅샷을 반환하는 유즈케이스.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetSessionUseCaseImpl implements GetSessionUseCase {

    private final AuthSupport authSupport;

    @Override
    public AuthSession handle() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ValidationException("Authentication is missing.");
        }
        return authSupport.buildSession(principal.getId(), principal.getAuthorities());
    }
}
