package com.base.application.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth.refresh-token")
public record RefreshTokenProperties(
        long ttlSeconds,
        String keyPrefix
) {
}