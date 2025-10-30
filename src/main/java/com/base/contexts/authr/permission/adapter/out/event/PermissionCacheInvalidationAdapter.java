package com.base.contexts.authr.permission.adapter.out.event;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.base.contexts.authr.permission.domain.port.out.PermissionCacheInvalidationPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class PermissionCacheInvalidationAdapter implements PermissionCacheInvalidationPort {

    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void invalidateRoleAuthorities(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        authorityCacheEventPort.publishRoleAuthoritiesChanged(userIds);
    }

    @Override
    public void invalidatePermissions(List<Long> userIds) {
        authorityCacheEventPort.publishPermissionsChanged(userIds);
    }
}
