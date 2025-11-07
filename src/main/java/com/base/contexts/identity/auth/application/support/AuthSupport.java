package com.base.contexts.identity.auth.application.support;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.base.contexts.authr.cache.domain.port.out.RefreshTokenStorePort;
import com.base.contexts.authr.cache.domain.port.out.RefreshTokenStorePort.StoredRefreshToken;
import com.base.contexts.identity.auth.application.UserAuthorityService;
import com.base.contexts.identity.auth.application.dto.AuthSession;
import com.base.contexts.identity.auth.application.dto.AuthUserSnapshot;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.port.out.UserQueryPort;
import com.base.platform.exception.NotFoundException;
import com.base.platform.exception.ValidationException;
import com.base.platform.security.jwt.JwtProperties;
import com.base.platform.security.jwt.JwtService;
import com.base.platform.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

/**
 * 인증 유즈케이스에서 공통으로 사용하는 헬퍼 컴포넌트.
 * 토큰 발급/저장/삭제와 사용자 세션 구성 로직을 한 곳으로 모아 재사용한다.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthSupport {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenStorePort refreshTokenStorePort;
    private final PasswordEncoder passwordEncoder;
    private final UserQueryPort userQueryPort;
    private final UserAuthorityService userAuthorityService;

    @Value("${security.cookies.secure:true}")
    private boolean secureCookies;

    /**
     * 주어진 Principal 정보를 기반으로 Access/Refresh 토큰을 생성한다.
     */
    public TokenBundle issueTokenBundle(UserPrincipal principal) {
        String refreshTokenId = UUID.randomUUID().toString();
        String accessTokenValue = jwtService.generateAccessToken(principal);
        String refreshTokenValue = jwtService.generateRefreshToken(principal, refreshTokenId);
        return new TokenBundle(accessTokenValue, refreshTokenValue, refreshTokenId);
    }

    @Transactional
    public void persistRefreshToken(Long userId, TokenBundle bundle) {
        refreshTokenStorePort.save(
                userId,
                bundle.refreshTokenId(),
                passwordEncoder.encode(bundle.refreshTokenValue()),
                Duration.ofSeconds(jwtProperties.refreshTokenExpirationSeconds())
        );
    }

    @Transactional
    public void revokeRefreshToken(Long userId, String refreshTokenId) {
        if (userId == null || !StringUtils.hasText(refreshTokenId)) {
            return;
        }
        refreshTokenStorePort.revoke(userId, refreshTokenId);
    }

    /**
     * Refresh 토큰의 유효성을 검증하고 사용자/토큰 식별자를 추출한다.
     */
    public ParsedRefreshToken parseRefreshToken(String refreshTokenValue) {
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
        return new ParsedRefreshToken(userId, tokenId, refreshTokenValue);
    }

    /**
     * 저장소에 보관된 해시와 전달된 토큰 값을 비교해 위·변조 여부를 확인한다.
     */
    @Transactional
    public void verifyStoredRefreshToken(ParsedRefreshToken parsedToken) {
        StoredRefreshToken storedToken = refreshTokenStorePort.find(parsedToken.userId(), parsedToken.tokenId())
                .orElseThrow(() -> new ValidationException("Invalid refresh token."));
        if (!passwordEncoder.matches(parsedToken.rawToken(), storedToken.tokenHash())) {
            refreshTokenStorePort.revoke(parsedToken.userId(), parsedToken.tokenId());
            throw new ValidationException("Invalid refresh token.");
        }
    }

    /**
     * Access/Refresh 토큰을 HttpOnly 쿠키로 내려보낼 수 있도록 빌드한다.
     */
    public List<ResponseCookie> buildIssueCookies(TokenBundle bundle) {
        return List.of(buildAccessCookie(bundle.accessTokenValue()), buildRefreshCookie(bundle.refreshTokenValue()));
    }

    /**
     * 클라이언트 보유 쿠키를 폐기하기 위한 공용 쿠키 목록을 반환한다.
     */
    public List<ResponseCookie> buildClearCookies() {
        return List.of(clearAccessCookie(), clearRefreshCookie());
    }

    /** 사용자 엔터티와 권한 정보를 조립해 세션 스냅샷을 만든다. */
    public AuthSession buildSession(Long userId, Collection<? extends GrantedAuthority> authorities) {
        User user = userQueryPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        AuthUserSnapshot userSnapshot = toSnapshot(user);
        List<String> permissions = resolvePermissions(userId, authorities);

        return new AuthSession(userSnapshot, permissions);
    }

    /**
     * Refresh 토큰 재발급 등에서 사용할 UserPrincipal 인스턴스를 생성한다.
     */
    public UserPrincipal buildPrincipal(Long userId, Collection<? extends GrantedAuthority> authorities) {
        User user = userQueryPort.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found."));
        Collection<? extends GrantedAuthority> resolved = (authorities == null || authorities.isEmpty())
                ? userAuthorityService.loadAuthoritiesOrEmpty(userId)
                : authorities;
        return UserPrincipal.from(user, resolved);
    }

    private List<String> resolvePermissions(Long userId, Collection<? extends GrantedAuthority> authorities) {
        Collection<? extends GrantedAuthority> source = (authorities == null || authorities.isEmpty())
                ? userAuthorityService.loadAuthoritiesOrEmpty(userId)
                : authorities;

        return source.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .map(String::trim)
                .distinct()
                .toList();
    }

    private AuthUserSnapshot toSnapshot(User user) {
        return new AuthUserSnapshot(
                user.getUserId() != null ? user.getUserId().value() : null,
                user.getEmail(),
                user.getLoginId(),
                user.getUserName(),
                user.getOrgId(),
                user.getEmpNo(),
                user.getPositionName(),
                user.getTel(),
                user.getUseYn()
        );
    }

    private ResponseCookie buildAccessCookie(String tokenValue) {
        return ResponseCookie.from("ACCESS_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtProperties.accessTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie buildRefreshCookie(String tokenValue) {
        return ResponseCookie.from("REFRESH_TOKEN", tokenValue)
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofSeconds(jwtProperties.refreshTokenExpirationSeconds()))
                .build();
    }

    private ResponseCookie clearAccessCookie() {
        return ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();
    }

    private ResponseCookie clearRefreshCookie() {
        return ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .secure(secureCookies)
                .sameSite("Lax")
                .path("/api/auth/refresh")
                .maxAge(Duration.ZERO)
                .build();
    }

    public record TokenBundle(
            String accessTokenValue,
            String refreshTokenValue,
            String refreshTokenId
    ) {
    }

    public record ParsedRefreshToken(
            Long userId,
            String tokenId,
            String rawToken
    ) {
    }
}
