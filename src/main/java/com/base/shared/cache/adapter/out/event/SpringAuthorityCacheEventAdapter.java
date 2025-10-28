package com.base.shared.cache.adapter.out.event;

import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.base.shared.cache.domain.event.PermissionChangedEvent;
import com.base.shared.cache.domain.event.RoleAuthorityChangedEvent;
import com.base.shared.cache.domain.port.out.AuthorityCacheEventPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class SpringAuthorityCacheEventAdapter implements AuthorityCacheEventPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishRoleAuthoritiesChanged(Collection<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        eventPublisher.publishEvent(new RoleAuthorityChangedEvent(List.copyOf(userIds)));
    }

    @Override
    public void publishPermissionsChanged(Collection<Long> userIds) {
        Collection<Long> safeUserIds = CollectionUtils.isEmpty(userIds) ? List.of() : List.copyOf(userIds);
        eventPublisher.publishEvent(new PermissionChangedEvent(safeUserIds));
    }
}
