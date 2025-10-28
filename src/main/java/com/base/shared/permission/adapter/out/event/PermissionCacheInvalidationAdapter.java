package com.base.shared.permission.adapter.out.event;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.application.event.publisher.CacheInvalidationEventPublisher;
import com.base.shared.permission.domain.port.out.PermissionCacheInvalidationPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class PermissionCacheInvalidationAdapter implements PermissionCacheInvalidationPort {

    private final CacheInvalidationEventPublisher cacheInvalidationEventPublisher;

    @Override
    public void invalidateRoleAuthorities(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        cacheInvalidationEventPublisher.publishRoleAuthorityChanged(userIds);
    }

    @Override
    public void invalidatePermissions(List<Long> userIds) {
        cacheInvalidationEventPublisher.publishPermissionChanged(userIds);
    }
}
