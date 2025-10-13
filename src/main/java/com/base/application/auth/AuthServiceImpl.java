package com.base.application.auth;

import java.time.Duration;
import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.api.auth.dto.LoginRequest;
import com.base.api.auth.dto.LoginResponse;
import com.base.api.auth.dto.LoginResult;
import com.base.api.user.dto.UserResponse;
import com.base.api.user.mapper.UserMapper;
import com.base.application.menu.query.UserMenuQueryService;
import com.base.domain.user.UserRepository;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;
import com.base.security.jwt.JwtProperties;
import com.base.security.jwt.JwtService;
import com.base.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager; // 자격 증명 검증기
    private final JwtService jwtService;                       // JWT 발급/검증기
    private final UserRepository userRepository;               // 사용자 조회
    private final UserMapper userMapper;                       // 사용자 응답 변환
    private final UserMenuQueryService userMenuQueryService;   // 사용자별 메뉴 조회
    private final JwtProperties jwtProperties;

    /**
     * 로그인 처리:
     * 1) AuthenticationManager로 인증
     * 2) 컨텍스트에 인증 저장
     * 3) JWT 발급
     * 4) 사용자 DTO 포함해 응답 구성
     */
    @Override
    @Transactional
    public LoginResult login(LoginRequest request) {
        Authentication authentication = authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        var userEntity = userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 새 로그인 시점이므로 Access/Refresh 토큰을 모두 재발급한다.
        // 새 로그인 시점이므로 Access/Refresh 토큰을 모두 재발급한다.
        ResponseCookie accessCookie = buildAccessCookie(jwtService.generateAccessToken(principal));
        ResponseCookie refreshCookie = buildRefreshCookie(jwtService.generateRefreshToken(principal));

        UserResponse user = userMapper.toResponse(userEntity);
        UserMenuQueryService.UserMenuAccessResult menuAccess =
                userMenuQueryService.getAccessibleMenus(principal.getId());

        LoginResponse responseBody = new LoginResponse(
                user,
                menuAccess.menuTree(),
                menuAccess.flatMenus()
        );

        return new LoginResult(responseBody, List.of(accessCookie, refreshCookie));
    }

    @Override
    @Transactional
    public LoginResult refresh(String refreshTokenValue) {
        if (!StringUtils.hasText(refreshTokenValue) || !jwtService.validateToken(refreshTokenValue)) {
            throw new ValidationException("Invalid refresh token.");
        }

        Long userId = jwtService.extractUserId(refreshTokenValue);
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found."));

        // Refresh 토큰에는 사용자 PK/로그인ID가 담겨 있으므로 그대로 재발급에 활용한다.
        UserPrincipal principal = UserPrincipal.from(userEntity);

        ResponseCookie accessCookie = buildAccessCookie(jwtService.generateAccessToken(principal));
        ResponseCookie refreshCookie = buildRefreshCookie(jwtService.generateRefreshToken(principal));

        UserResponse user = userMapper.toResponse(userEntity);
        UserMenuQueryService.UserMenuAccessResult menuAccess =
                userMenuQueryService.getAccessibleMenus(userId);

        LoginResponse responseBody = new LoginResponse(
                user,
                menuAccess.menuTree(),
                menuAccess.flatMenus()
        );

        return new LoginResult(responseBody, List.of(accessCookie, refreshCookie));
    }

    @Override
    @Transactional
    public List<ResponseCookie> logout(String refreshTokenValue) {
        // TODO: 추후 DB 기반 세션 관리를 도입하면 여기서 서버 측 Refresh 토큰을 폐기한다.
        return List.of(clearAccessCookie(), clearRefreshCookie());
    }

    private ResponseCookie buildAccessCookie(String tokenValue) {
        // Access 토큰은 짧은 수명을 가지며, 모든 요청에 자동 포함되도록 HttpOnly 쿠키에 저장한다.
        return ResponseCookie.from("ACCESS_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtProperties.accessTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie buildRefreshCookie(String tokenValue) {
        // Refresh 토큰은 재발급 엔드포인트에서만 사용되도록 path를 제한한다.
        return ResponseCookie.from("REFRESH_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofSeconds(jwtProperties.refreshTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie clearAccessCookie() {
        // maxAge=0 으로 내려 폐기한다.
        return ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
    }

    private ResponseCookie clearRefreshCookie() {
        // maxAge=0 으로 내려 폐기한다.
        return ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/api/auth/refresh")
                .maxAge(Duration.ZERO)
                .build();
    }

    /**
     * 아이디/비밀번호로 인증 시도.
     * 실패 시 ValidationException으로 변환.
     */
    private Authentication authenticate(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.loginId(), request.password());
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex) {
            throw new ValidationException("Invalid login credentials.");
        }
    }
}
