package com.base.application.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.base.application.event.model.PermissionChangedEvent;
import com.base.application.event.model.RoleAuthorityChangedEvent;
import com.base.infra.redis.cache.AuthorityCacheService;
import com.base.infra.redis.cache.PermissionCacheService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityCacheEventListener {

    private final AuthorityCacheService authorityCacheService;
    private final PermissionCacheService permissionCacheService;

    @EventListener
    public void handlePermissionChanged(PermissionChangedEvent event) {
        if (!CollectionUtils.isEmpty(event.affectedUserIds())) {
            authorityCacheService.evictAll(event.affectedUserIds());
        }
        permissionCacheService.evictAll();
    }

    @EventListener
    public void handleRoleAuthorityChanged(RoleAuthorityChangedEvent event) {
        if (!CollectionUtils.isEmpty(event.userIds())) {
            authorityCacheService.evictAll(event.userIds());
        }
    }
}
