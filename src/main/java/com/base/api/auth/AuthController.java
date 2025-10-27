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

import com.base.api.auth.assembler.AuthCommandAssembler;
import com.base.api.auth.assembler.AuthResponseAssembler;
import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResponse;
import com.base.api.auth.dto.LoginResult;
import com.base.application.auth.usecase.command.LogoutCommand;
import com.base.application.auth.usecase.command.RefreshTokenCommand;
import com.base.application.auth.usecase.login.LoginUseCase;
import com.base.application.auth.usecase.logout.LogoutUseCase;
import com.base.application.auth.usecase.refresh.RefreshTokenUseCase;
import com.base.application.auth.usecase.result.AuthExecutionResult;
import com.base.application.auth.usecase.result.AuthSession;
import com.base.application.auth.usecase.result.LogoutExecutionResult;
import com.base.application.auth.usecase.session.GetSessionUseCase;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final GetSessionUseCase getSessionUseCase;
    private final AuthCommandAssembler authCommandAssembler;
    private final AuthResponseAssembler authResponseAssembler;
    private final CsrfTokenRepository csrfTokenRepository;

    /**
     * 로그인 엔드포인트.
     * - 응답 본문에는 사용자/메뉴 정보만 담고, 토큰은 HttpOnly 쿠키로 내려보낸다.
     * - 인증 실패 시 {@link LoginUseCase} 내부에서 ValidationException을 던진다.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest httpRequest,
                                               HttpServletResponse httpResponse,
                                               @RequestBody @Valid LoginRequest request) {
        AuthExecutionResult executionResult = loginUseCase.handle(authCommandAssembler.toLoginCommand(request));
        LoginResult loginResult = authResponseAssembler.toLoginResult(executionResult);

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

        AuthExecutionResult executionResult = refreshTokenUseCase.handle(authCommandAssembler.toRefreshCommand(refreshToken));
        LoginResult refreshResult = authResponseAssembler.toLoginResult(executionResult);
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
        LogoutExecutionResult logoutResult = logoutUseCase.handle(authCommandAssembler.toLogoutCommand(refreshToken));
        List<ResponseCookie> cookies = logoutResult.cookies();
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.NO_CONTENT);
        cookies.forEach(cookie -> builder.header(HttpHeaders.SET_COOKIE, cookie.toString()));
        return builder.build();
    }

    /** 현재 인증된 사용자 정보를 조회한다. */
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me() {
        AuthSession session = getSessionUseCase.handle();
        LoginResponse response = authResponseAssembler.toLoginResponse(session);
        return ResponseEntity.ok(response);
    }
}
