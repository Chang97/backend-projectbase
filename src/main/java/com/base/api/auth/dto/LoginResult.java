package com.base.api.auth.dto;

import java.util.List;

import org.springframework.http.ResponseCookie;

/**
 * 인증 관련 응답을 헤더/본문으로 나누어 반환하기 위한 래퍼.
 * - body: 실제 API 응답 본문 (사용자 정보, 메뉴 등)
 * - cookies: 컨트롤러에서 Set-Cookie 헤더에 실어 보낼 쿠키 목록
 */
public record LoginResult(
        LoginResponse body,
        List<ResponseCookie> cookies
) {}
