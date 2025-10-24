package com.base.application.auth;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.base.infra.redis.token.RefreshTokenStore;
import com.base.infra.redis.token.RefreshTokenStore.StoredRefreshToken;
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
    private final RefreshTokenStore refreshTokenStore;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthorityService userAuthorityService;

    @Value("${security.cookies.secure:true}")
    private boolean secureCookies;

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

        String refreshTokenId = UUID.randomUUID().toString();
        String accessTokenValue = jwtService.generateAccessToken(principal);
        String refreshTokenValue = jwtService.generateRefreshToken(principal, refreshTokenId);

        persistRefreshToken(userEntity.getUserId(), refreshTokenValue, refreshTokenId);

        ResponseCookie accessCookie = buildAccessCookie(accessTokenValue);
        ResponseCookie refreshCookie = buildRefreshCookie(refreshTokenValue);

        UserResponse user = userMapper.toResponse(userEntity);
        UserMenuQueryService.UserMenuAccessResult menuAccess =
                userMenuQueryService.getAccessibleMenus(principal.getId());

        LoginResponse responseBody = new LoginResponse(
                user,
                menuAccess.menuTree(),
                menuAccess.flatMenus(),
                resolvePermissions(principal.getId(), principal.getAuthorities())
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
        String tokenId;
        try {
            tokenId = jwtService.extractTokenId(refreshTokenValue);
        } catch (IllegalStateException ex) {
            throw new ValidationException("Invalid refresh token.");
        }

        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found."));

        StoredRefreshToken storedToken = refreshTokenStore.find(userId, tokenId)
                .orElseThrow(() -> new ValidationException("Invalid refresh token."));

        if (!passwordEncoder.matches(refreshTokenValue, storedToken.tokenHash())) {
            refreshTokenStore.revoke(userId, tokenId);
            throw new ValidationException("Invalid refresh token.");
        }

        // Refresh 토큰에는 사용자 PK/로그인ID가 담겨 있으므로 그대로 재발급에 활용한다.
        UserPrincipal principal = UserPrincipal.from(
                userEntity,
                userAuthorityService.loadAuthoritiesOrEmpty(userEntity.getUserId())
        );

        String newRefreshTokenId = UUID.randomUUID().toString();
        String accessTokenValue = jwtService.generateAccessToken(principal);
        String newRefreshTokenValue = jwtService.generateRefreshToken(principal, newRefreshTokenId);

        refreshTokenStore.revoke(userId, tokenId);
        persistRefreshToken(userId, newRefreshTokenValue, newRefreshTokenId);

        ResponseCookie accessCookie = buildAccessCookie(accessTokenValue);
        ResponseCookie refreshCookie = buildRefreshCookie(newRefreshTokenValue);

        UserResponse user = userMapper.toResponse(userEntity);
        UserMenuQueryService.UserMenuAccessResult menuAccess =
                userMenuQueryService.getAccessibleMenus(userId);

        LoginResponse responseBody = new LoginResponse(
                user,
                menuAccess.menuTree(),
                menuAccess.flatMenus(),
                resolvePermissions(userId, principal.getAuthorities())
        );

        return new LoginResult(responseBody, List.of(accessCookie, refreshCookie));
    }

    @Override
    @Transactional
    public List<ResponseCookie> logout(String refreshTokenValue) {
        revokeRefreshToken(refreshTokenValue);
        return List.of(clearAccessCookie(), clearRefreshCookie());
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ValidationException("Authentication is missing.");
        }

        var userEntity = userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserResponse user = userMapper.toResponse(userEntity);
        UserMenuQueryService.UserMenuAccessResult menuAccess =
                userMenuQueryService.getAccessibleMenus(principal.getId());

        return new LoginResponse(
                user,
                menuAccess.menuTree(),
                menuAccess.flatMenus(),
                resolvePermissions(principal.getId(), principal.getAuthorities())
        );
    }

    private ResponseCookie buildAccessCookie(String tokenValue) {
        // Access 토큰은 짧은 수명을 가지며, 모든 요청에 자동 포함되도록 HttpOnly 쿠키에 저장한다.
        return ResponseCookie.from("ACCESS_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtProperties.accessTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie buildRefreshCookie(String tokenValue) {
        // Refresh 토큰은 재발급 엔드포인트에서만 사용되도록 path를 제한한다.
        return ResponseCookie.from("REFRESH_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofSeconds(jwtProperties.refreshTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie clearAccessCookie() {
        // maxAge=0 으로 내려 폐기한다.
        return ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
    }

    private ResponseCookie clearRefreshCookie() {
        // maxAge=0 으로 내려 폐기한다.
        return ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .secure(secureCookies)
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

    private void persistRefreshToken(Long userId, String tokenValue, String tokenId) {
        refreshTokenStore.save(
                userId,
                tokenId,
                passwordEncoder.encode(tokenValue),
                Duration.ofSeconds(jwtProperties.refreshTokenExpirationSeconds())
        );
    }

    private void revokeRefreshToken(String refreshTokenValue) {
        if (!StringUtils.hasText(refreshTokenValue) || !jwtService.validateToken(refreshTokenValue)) {
            return;
        }
        try {
            Long userId = jwtService.extractUserId(refreshTokenValue);
            String tokenId = jwtService.extractTokenId(refreshTokenValue);
            refreshTokenStore.revoke(userId, tokenId);
        } catch (Exception ignored) {
            // 토큰 파싱에 실패하면 이미 유효하지 않다고 판단하고 넘어간다.
        }
    }

    private List<String> resolvePermissions(Long userId, Collection<? extends GrantedAuthority> authorities) {
        Collection<? extends GrantedAuthority> source =
                (authorities == null || authorities.isEmpty())
                        ? userAuthorityService.loadAuthoritiesOrEmpty(userId)
                        : authorities;

        return source.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .map(String::trim)
                .distinct()
                .toList();
    }
}
