package com.base.application.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.base.application.auth.port.AuthorityCachePort;
import com.base.application.auth.port.PermissionCachePort;
import com.base.application.event.model.PermissionChangedEvent;
import com.base.application.event.model.RoleAuthorityChangedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityCacheEventListener {

    private final AuthorityCachePort authorityCachePort;
    private final PermissionCachePort permissionCachePort;

    @EventListener
    public void handlePermissionChanged(PermissionChangedEvent event) {
        if (!CollectionUtils.isEmpty(event.affectedUserIds())) {
            authorityCachePort.evictAll(event.affectedUserIds());
        }
        permissionCachePort.evictAll();
    }

    @EventListener
    public void handleRoleAuthorityChanged(RoleAuthorityChangedEvent event) {
        if (!CollectionUtils.isEmpty(event.userIds())) {
            authorityCachePort.evictAll(event.userIds());
        }
    }
}
