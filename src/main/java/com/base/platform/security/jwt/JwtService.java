package com.base.platform.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.base.platform.security.userdetails.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;

@Component
public class JwtService {

    private static final int MIN_SECRET_LENGTH = 64;

    private final JwtProperties properties; // 설정 값
    private SecretKey secretKey;            // 서명 키

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    // 애플리케이션 시작 시 비밀키 객체 생성
    @PostConstruct
    void init() {
        String secret = properties.secret();
        if (!StringUtils.hasText(secret)) {
            throw new IllegalStateException("JWT secret must be provided via the JWT_SECRET environment variable or jwt.secret property.");
        }
        if (secret.length() < MIN_SECRET_LENGTH) {
            throw new IllegalStateException("JWT secret must be at least " + MIN_SECRET_LENGTH + " characters long.");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 액세스 토큰 생성.
     * - subject: 로그인 ID
     * - uid: 사용자 PK
     * - exp/iat: 만료/발급 시각
     */
    public String generateAccessToken(UserPrincipal principal) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(properties.accessTokenExpirationSeconds());
        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
                .setHeaderParam("typ", "JWT")
                .setSubject(principal.getUsername())
                .claim("uid", principal.getId())
                .claim("cs", now.getEpochSecond())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 생성(Access와 동일 구조지만 유효기간만 길게 설정)
    public String generateRefreshToken(UserPrincipal principal, String tokenId) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(properties.refreshTokenExpirationSeconds());
        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
                .setHeaderParam("typ", "JWT")
                .setSubject(principal.getUsername())
                .claim("uid", principal.getId())
                .claim("tid", tokenId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 유효성 단순 검사(파싱 가능 여부)
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // 토큰에서 사용자 PK 추출
    public Long extractUserId(String token) {
        return parseClaims(token).get("uid", Long.class);
    }

    // 토큰에서 로그인 ID(subject) 추출
    public String extractLoginId(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractTokenId(String token) {
        String tokenId = parseClaims(token).get("tid", String.class);
        if (!StringUtils.hasText(tokenId)) {
            throw new IllegalStateException("Refresh token is missing token identifier.");
        }
        return tokenId;
    }

    // 토큰 만료 시각(UTC) 반환
    public LocalDateTime extractExpiration(String token) {
        Date expiration = parseClaims(token).getExpiration();
        return LocalDateTime.ofInstant(expiration.toInstant(), ZoneOffset.UTC);
    }

    // 내부 파싱 공통 로직
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
