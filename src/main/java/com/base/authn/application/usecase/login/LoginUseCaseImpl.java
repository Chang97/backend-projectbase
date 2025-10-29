package com.base.authn.application.usecase.login;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.authn.application.usecase.command.LoginCommand;
import com.base.authn.application.usecase.result.AuthExecutionResult;
import com.base.authn.application.usecase.result.AuthSession;
import com.base.authn.application.usecase.support.AuthSupport;
import com.base.authn.application.usecase.support.AuthSupport.TokenBundle;
import com.base.exception.ValidationException;
import com.base.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 유즈케이스 구현체.
 * <p>
 * 1) 인증 매니저를 통해 자격 증명을 검증하고
 * 2) Refresh/Access 토큰을 발급 및 저장한 뒤
 * 3) 사용자 세션 정보를 조립해 반환한다.
 */
@Service
@RequiredArgsConstructor
@Transactional
class LoginUseCaseImpl implements LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final AuthSupport authSupport;

    @Override
    public AuthExecutionResult handle(LoginCommand command) {
        // 1. 자격 증명을 검증한다. 실패 시 ValidationException이 발생한다.
        Authentication authentication = authenticate(command);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // 2. 토큰을 발급하고 저장소에 최신 Refresh 토큰 정보를 기록한다.
        TokenBundle tokenBundle = authSupport.issueTokenBundle(principal);
        authSupport.persistRefreshToken(principal.getId(), tokenBundle);

        // 3. 현재 사용자가 접근 가능한 메뉴/권한 정보를 구성한다.
        AuthSession session = authSupport.buildSession(principal.getId(), principal.getAuthorities());
        List<ResponseCookie> cookies = authSupport.buildIssueCookies(tokenBundle);

        return new AuthExecutionResult(session, cookies);
    }

    private Authentication authenticate(LoginCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(command.loginId(), command.password());
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            throw new ValidationException("Invalid login credentials.");
        }
    }
}
