package com.base.infra.redis.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.permission.cache")
public record PermissionCacheProperties(
        long ttlSeconds,
        String keyPrefix
) {
}
