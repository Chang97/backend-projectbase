package com.base.api.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResponse;
import com.base.api.auth.dto.LoginResult;
import com.base.application.auth.AuthService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    // 인증 업무 위임 대상 서비스
    private final AuthService authService;
    private final CsrfTokenRepository csrfTokenRepository;

    /**
     * 로그인 엔드포인트.
     * - 응답 본문에는 사용자/메뉴 정보만 담고, 토큰은 HttpOnly 쿠키로 내려보낸다.
     * - 인증 실패 시 AuthService 내부에서 ValidationException 발생.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest httpRequest,
                                               HttpServletResponse httpResponse,
                                               @RequestBody @Valid LoginRequest request) {
        LoginResult loginResult = authService.login(request);

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        loginResult.cookies().forEach(cookie ->
                builder.header(HttpHeaders.SET_COOKIE, cookie.toString()));

        CsrfToken csrfToken = csrfTokenRepository.generateToken(httpRequest);
        csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);
        builder.header("X-CSRF-TOKEN", csrfToken.getToken());

        return builder.body(loginResult.body());
    }
    /**
     * Refresh 쿠키만으로 새 액세스/리프레시 토큰을 재발급한다.
     * 브라우저에서 쿠키를 함께 전송해야 하므로 withCredentials 설정이 필요하다.
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest httpRequest,
                                                 HttpServletResponse httpResponse,
                                                 @CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResult refreshResult = authService.refresh(refreshToken);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        refreshResult.cookies().forEach(cookie ->
                builder.header(HttpHeaders.SET_COOKIE, cookie.toString()));

        CsrfToken csrfToken = csrfTokenRepository.generateToken(httpRequest);
        csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);
        builder.header("X-CSRF-TOKEN", csrfToken.getToken());

        return builder.body(refreshResult.body());
    }

    /**
     * 서버 측 상태는 아직 없으므로 쿠키를 만료시키는 것만으로 로그아웃을 처리한다.
     * (추후 Refresh 토큰 저장소를 도입하면 revoke 로직 추가 예정)
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
        List<ResponseCookie> cookies = authService.logout(refreshToken);
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.NO_CONTENT);
        cookies.forEach(cookie -> builder.header(HttpHeaders.SET_COOKIE, cookie.toString()));
        return builder.build();
    }

    /** 현재 인증된 사용자 정보를 조회한다. */
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me() {
        return ResponseEntity.ok(authService.me());
    }
}
