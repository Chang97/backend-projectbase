package com.base.infra.redis.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth.refresh-token")
public record RefreshTokenProperties(
        long ttlSeconds,
        String keyPrefix
) {
}