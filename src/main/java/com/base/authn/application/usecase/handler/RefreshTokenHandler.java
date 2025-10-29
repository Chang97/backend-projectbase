package com.base.authn.application.usecase.handler;

import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.authn.application.usecase.dto.RefreshTokenCommand;
import com.base.authn.application.usecase.port.in.RefreshTokenUseCase;
import com.base.authn.application.usecase.result.AuthExecutionResult;
import com.base.authn.application.usecase.result.AuthSession;
import com.base.authn.application.usecase.support.AuthSupport;
import com.base.authn.application.usecase.support.AuthSupport.ParsedRefreshToken;
import com.base.authn.application.usecase.support.AuthSupport.TokenBundle;
import com.base.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * Refresh 토큰으로 새로운 세션을 재발급하는 유즈케이스.
 * <p>
 * 1) 전달된 Refresh 토큰을 검증하고
 * 2) 저장된 토큰 해시와 일치 여부를 확인한 뒤
 * 3) 새로운 토큰과 세션 정보를 만들어 반환한다.
 */
@Service
@RequiredArgsConstructor
@Transactional
class RefreshTokenHandler implements RefreshTokenUseCase {

    private final AuthSupport authSupport;

    @Override
    public AuthExecutionResult handle(RefreshTokenCommand command) {
        // 1. 전달된 Refresh 토큰이 유효한지 검증하고 토큰 ID/사용자 ID를 추출한다.
        ParsedRefreshToken parsedToken = authSupport.parseRefreshToken(command.refreshToken());
        // 2. 저장소에 기록된 해시와 비교해 위·변조 여부를 확인한다.
        authSupport.verifyStoredRefreshToken(parsedToken);

        UserPrincipal principal = authSupport.buildPrincipal(parsedToken.userId(), List.of());

        // 3. 새 토큰을 발급하고 기존 토큰을 폐기한다.
        TokenBundle tokenBundle = authSupport.issueTokenBundle(principal);
        authSupport.revokeRefreshToken(parsedToken.userId(), parsedToken.tokenId());
        authSupport.persistRefreshToken(parsedToken.userId(), tokenBundle);

        // 4. 사용자 세션 정보를 재구성한다.
        AuthSession session = authSupport.buildSession(parsedToken.userId(), principal.getAuthorities());
        List<ResponseCookie> cookies = authSupport.buildIssueCookies(tokenBundle);

        return new AuthExecutionResult(session, cookies);
    }
}
