package com.base.contexts.authr.permission.application.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.contexts.authr.cache.domain.port.out.PermissionCachePort;
import com.base.contexts.authr.permission.application.query.mapper.PermissionQueryMapper;
import com.base.contexts.authr.permission.domain.model.PermissionFilter;
import com.base.contexts.authr.permission.domain.model.PermissionSnapshot;
import com.base.contexts.authr.permission.domain.port.out.PermissionQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionCatalogService {

    private final PermissionQueryPort permissionQueryPort;
    private final PermissionCachePort permissionCachePort;
    private final PermissionQueryMapper permissionQueryMapper;

    public List<PermissionSnapshot> getActivePermissions() {
        return permissionCachePort
                .get(null, true)   // 전체 + useYn=true 조합 키
                .orElseGet(this::loadAndCacheActivePermissions);
    }

    private List<PermissionSnapshot> loadAndCacheActivePermissions() {
        PermissionFilter filter = new PermissionFilter(null, null, null, true);
        List<PermissionSnapshot> snapshots = permissionQueryPort.findAll(filter).stream()
                .map(permissionQueryMapper::toSnapshot)
                .toList();
        permissionCachePort.put(null, true, snapshots);
        return snapshots;
    }
}
