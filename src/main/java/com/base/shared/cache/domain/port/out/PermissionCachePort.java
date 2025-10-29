package com.base.shared.cache.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.shared.permission.application.query.dto.PermissionQueryResult;

public interface PermissionCachePort {

    Optional<List<PermissionQueryResult>> get(String permissionName, Boolean useYn);

    void put(String permissionName, Boolean useYn, List<PermissionQueryResult> responses);

    void evict(String permissionName, Boolean useYn);

    void evictAll();
}
