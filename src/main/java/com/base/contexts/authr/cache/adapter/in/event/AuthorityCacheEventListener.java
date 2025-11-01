package com.base.contexts.authr.cache.adapter.in.event;

import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.CollectionUtils;

import com.base.contexts.authr.cache.domain.event.PermissionChangedEvent;
import com.base.contexts.authr.cache.domain.event.RoleAuthorityChangedEvent;
import com.base.contexts.authr.cache.domain.port.out.AuthorityCachePort;
import com.base.contexts.authr.cache.domain.port.out.PermissionCachePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorityCacheEventListener {

    private final AuthorityCachePort authorityCachePort;
    private final PermissionCachePort permissionCachePort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRoleAuthoritiesChanged(RoleAuthorityChangedEvent event) {
        Collection<Long> userIds = event.userIds();
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        authorityCachePort.evictAll(userIds);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPermissionsChanged(PermissionChangedEvent event) {
        permissionCachePort.evictAll();
        Collection<Long> userIds = event.affectedUserIds();
        if (!CollectionUtils.isEmpty(userIds)) {
            authorityCachePort.evictAll(userIds);
        }
    }
}
