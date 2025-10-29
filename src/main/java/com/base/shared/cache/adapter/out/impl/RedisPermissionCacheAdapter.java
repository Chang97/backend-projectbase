package com.base.shared.cache.adapter.out.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.base.shared.cache.domain.port.out.PermissionCachePort;
import com.base.shared.permission.application.query.dto.PermissionQueryResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPermissionCacheAdapter implements PermissionCachePort {

    private final PermissionCacheAdapter permissionCacheAdapter;

    @Override
    public Optional<List<PermissionQueryResult>> get(String permissionName, Boolean useYn) {
        return permissionCacheAdapter.get(permissionName, useYn);
    }

    @Override
    public void put(String permissionName, Boolean useYn, List<PermissionQueryResult> responses) {
        permissionCacheAdapter.put(permissionName, useYn, responses);
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
