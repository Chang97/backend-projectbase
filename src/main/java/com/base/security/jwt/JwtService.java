package com.base.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.base.security.userdetails.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;

@Component
public class JwtService {

    private final JwtProperties properties; // 설정 값
    private SecretKey secretKey;            // 서명 키

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    // 애플리케이션 시작 시 비밀키 객체 생성
    @PostConstruct
    void init() {
        this.secretKey = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
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
                .setSubject(principal.getUsername())
                .claim("uid", principal.getId())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 생성(Access와 동일 구조지만 유효기간만 길게 설정)
    public String generateRefreshToken(UserPrincipal principal) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(properties.refreshTokenExpirationSeconds());
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("uid", principal.getId())
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

    // 토큰 만료 시각(UTC) 반환
    public OffsetDateTime extractExpiration(String token) {
        Date expiration = parseClaims(token).getExpiration();
        return OffsetDateTime.ofInstant(expiration.toInstant(), ZoneOffset.UTC);
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
