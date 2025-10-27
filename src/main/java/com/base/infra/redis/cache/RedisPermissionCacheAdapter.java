package com.base.infra.redis.cache;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.application.auth.port.PermissionCachePort;
import com.base.application.permission.usecase.result.PermissionResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPermissionCacheAdapter implements PermissionCachePort {

    private final PermissionCacheService permissionCacheService;

    @Override
    public Optional<List<PermissionResult>> get(String permissionName, Boolean useYn) {
        return permissionCacheService.get(permissionName, useYn);
    }

    @Override
    public void put(String permissionName, Boolean useYn, List<PermissionResult> responses) {
        permissionCacheService.put(permissionName, useYn, responses);
    }

    @Override
    public void evict(String permissionName, Boolean useYn) {
        permissionCacheService.evict(permissionName, useYn);
    }

    @Override
    public void evictAll() {
        permissionCacheService.evictAll();
    }
}
