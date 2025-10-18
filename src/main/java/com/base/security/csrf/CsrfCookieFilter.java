package com.base.security.csrf;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 매 요청마다 생성된 CSRF 토큰을 XSRF-TOKEN 쿠키와 동기화한다.
 */
@Component
public class CsrfCookieFilter extends OncePerRequestFilter {

    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            String tokenValue = csrfToken.getToken();
            Cookie existingCookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
            boolean shouldUpdate = existingCookie == null || tokenValue != null && !tokenValue.equals(existingCookie.getValue());
            if (shouldUpdate && tokenValue != null) {
                ResponseCookie cookie = ResponseCookie.from(CSRF_COOKIE_NAME, tokenValue)
                        .httpOnly(false)
                        .secure(request.isSecure())
                        .sameSite("Lax")
                        .path("/")
                        .build();
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            }
        }

        filterChain.doFilter(request, response);
    }
}
