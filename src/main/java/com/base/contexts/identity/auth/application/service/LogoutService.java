package com.base.contexts.identity.auth.application.service;

import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.contexts.identity.auth.application.dto.LogoutCommand;
import com.base.contexts.identity.auth.application.dto.LogoutExecutionResult;
import com.base.contexts.identity.auth.application.port.in.LogoutUseCase;
import com.base.contexts.identity.auth.application.support.AuthSupport;
import com.base.contexts.identity.auth.application.support.AuthSupport.ParsedRefreshToken;
import com.base.platform.exception.ValidationException;

import lombok.RequiredArgsConstructor;
/**
 * 로그아웃 유즈케이스.
 * Refresh 토큰이 전달되면 저장소에서 무효화하고,
 * 항상 ACCESS/REFRESH 쿠키를 폐기하도록 응답한다.
 */
@Service
@RequiredArgsConstructor
@Transactional
class LogoutService implements LogoutUseCase {

    private final AuthSupport authSupport;

    @Override
    public LogoutExecutionResult handle(LogoutCommand command) {
        String refreshToken = command.refreshToken();
        if (StringUtils.hasText(refreshToken)) {
            try {
                ParsedRefreshToken parsedToken = authSupport.parseRefreshToken(refreshToken);
                authSupport.revokeRefreshToken(parsedToken.userId(), parsedToken.tokenId());
            } catch (ValidationException ignored) {
                // 토큰이 유효하지 않다면 이미 만료된 것으로 간주하고 넘어간다.
            }
        }

        List<ResponseCookie> cookies = authSupport.buildClearCookies();
        return new LogoutExecutionResult(cookies);
    }
}
