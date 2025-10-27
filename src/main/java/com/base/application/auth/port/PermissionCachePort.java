package com.base.application.auth.port;

import java.util.List;
import java.util.Optional;

import com.base.application.permission.usecase.result.PermissionResult;

public interface PermissionCachePort {

    Optional<List<PermissionResult>> get(String permissionName, Boolean useYn);

    void put(String permissionName, Boolean useYn, List<PermissionResult> responses);

    void evict(String permissionName, Boolean useYn);

    void evictAll();
}
