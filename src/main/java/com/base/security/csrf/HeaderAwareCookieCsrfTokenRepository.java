package com.base.security.csrf;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieCsrfTokenRepository 기반으로 동작하지만 쿠키가 없을 때 헤더 값을 그대로 신뢰한다.
 */
public class HeaderAwareCookieCsrfTokenRepository implements CsrfTokenRepository {

    private final String cookieName;
    private final String headerName;
    private final String parameterName;
    private final boolean httpOnly;
    private static final Logger log = LoggerFactory.getLogger(HeaderAwareCookieCsrfTokenRepository.class);

    public HeaderAwareCookieCsrfTokenRepository() {
        this("XSRF-TOKEN", "X-CSRF-TOKEN", "_csrf", false);
    }

    public HeaderAwareCookieCsrfTokenRepository(String cookieName, String headerName, String parameterName, boolean httpOnly) {
        this.cookieName = cookieName;
        this.headerName = headerName;
        this.parameterName = parameterName;
        this.httpOnly = httpOnly;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String token = UUID.randomUUID().toString().replaceAll("\\-", "");
        return new DefaultCsrfToken(headerName, parameterName, token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                    .maxAge(0)
                    .path("/")
                    .sameSite("Lax")
                    .httpOnly(httpOnly)
                    .secure(request.isSecure())
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return;
        }

        ResponseCookie cookie = ResponseCookie.from(cookieName, token.getToken())
                .maxAge(-1)
                .path("/")
                .sameSite("Lax")
                .httpOnly(httpOnly)
                .secure(request.isSecure())
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie != null && StringUtils.hasText(cookie.getValue())) {
            log.debug("loadToken: using cookie {}", cookie.getValue());
            return new DefaultCsrfToken(headerName, parameterName, cookie.getValue());
        }
        String headerToken = request.getHeader(headerName);
        if (StringUtils.hasText(headerToken)) {
            log.debug("loadToken: using header {}", headerToken);
            return new DefaultCsrfToken(headerName, parameterName, headerToken);
        }
        log.debug("loadToken: no token found");
        return null;
    }
}
