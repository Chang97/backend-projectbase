package com.base.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 내 jwt.* 값을 바인딩.
 * 예)
 * jwt:
 *   secret: "32바이트 이상 랜덤"
 *   accessTokenExpirationSeconds: 1800
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        long accessTokenExpirationSeconds,
        long refreshTokenExpirationSeconds
) {
}
