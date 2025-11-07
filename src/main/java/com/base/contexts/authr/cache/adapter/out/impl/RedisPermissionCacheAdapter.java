package com.base.contexts.authr.cache.adapter.out.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.cache.domain.port.out.PermissionCachePort;
import com.base.contexts.authr.permission.domain.model.PermissionSnapshot;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPermissionCacheAdapter implements PermissionCachePort {

    private final PermissionCacheAdapter permissionCacheAdapter;

    @Override
    public Optional<List<PermissionSnapshot>> get(String permissionName, Boolean useYn) {
        return permissionCacheAdapter.get(permissionName, useYn);
    }

    @Override
    public void put(String permissionName, Boolean useYn, List<PermissionSnapshot> permissions) {
        permissionCacheAdapter.put(permissionName, useYn, permissions);
    }

    @Override
    public void evict(String permissionName, Boolean useYn) {
        permissionCacheAdapter.evict(permissionName, useYn);
    }

    @Override
    public void evictAll() {
        permissionCacheAdapter.evictAll();
    }
}
