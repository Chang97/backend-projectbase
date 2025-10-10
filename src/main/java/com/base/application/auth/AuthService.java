package com.base.application.auth;

import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResponse;

/**
 * 인증 유스케이스 인터페이스.
 * - 구현체가 Spring Security와 JWT 발급을 수행.
 */
public interface AuthService {

    LoginResponse login(LoginRequest request);
}
