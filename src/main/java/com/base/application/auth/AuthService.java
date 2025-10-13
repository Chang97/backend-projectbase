package com.base.application.auth;

import java.util.List;

import org.springframework.http.ResponseCookie;

import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResult;

/**
 * 인증 유스케이스 인터페이스.
 * - 구현체가 Spring Security와 JWT 발급을 수행.
 */
public interface AuthService {

    /** 로그인 처리 후 본문 + 쿠키 정보를 반환한다. */
    LoginResult login(LoginRequest request);

    /** Refresh 토큰 쿠키를 이용해 세션을 연장한다. */
    LoginResult refresh(String refreshTokenValue);

    /** 세션 종료 시 클라이언트 쿠키를 만료시키기 위한 쿠키 목록을 반환한다. */
    List<ResponseCookie> logout(String refreshTokenValue);
}
